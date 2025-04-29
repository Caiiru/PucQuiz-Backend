package com.graspfy.graspit.User


import com.graspfy.graspit.Exception.BadRequestException
import com.graspfy.graspit.Exception.NotFoundException
import com.graspfy.graspit.Role.RoleRepository
import com.graspfy.graspit.User.Controller.Response.LoginResponse
import com.graspfy.graspit.User.Controller.Response.UserResponse
import com.graspfy.graspit.security.JWT
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val jwt: JWT
) {

    fun insertUser(user: User):User{
        if (userRepository.findByEmail(user.email)!=null){
            throw BadRequestException("User Already Exists")
        }
        return userRepository.save(user).also { log.info("user inserted: {}",it.id!!)}
    }

    fun getUsers(dir:SortDir):List<User> =
        when(dir) {
            SortDir.ASC -> userRepository.findAll().sortedBy { it.name }
            SortDir.DESC -> userRepository.findAll().sortedByDescending { it.name }
        }

    fun findAll(dir:SortDir, role:String?)=
        role?.let {
            when (dir) {
                SortDir.ASC -> userRepository.findByRole(role).sortedBy { it.name }
                SortDir.DESC -> userRepository.findByRole(role).sortedByDescending { it.name }
            }
        }?: when (dir) {
            SortDir.ASC -> userRepository.findAll().sortedBy { it.name }
            SortDir.DESC -> userRepository.findAll().sortedByDescending { it.name }
        }
    fun findByIdOrNull(id:Long):User? = userRepository.findByIdOrNull(id)
    fun findByIdOrThrow(id:Long):User = findByIdOrNull(id)?: throw NotFoundException("User with id $id not found")
    fun getUsersByRole(role:String)=userRepository.findByRole(role)

    fun update(id:Long, name:String):User?{
        val user = userRepository.findByIdOrNull(id)?: throw NotFoundException("User with id $id not found")
        user.name = name
        return userRepository.save(user)
    }




    fun getUserOrNull(id:Long) = userRepository.findByIdOrNull(id)

    fun removeUserByID(id:Long):Boolean {
        val user = userRepository.findByIdOrNull(id)?:return false;

        if(user.roles.any{it.name=="ADMIN"}){
            val count = userRepository.findByRole("ADMIN").size
            if(count == 1)
                throw BadRequestException("Cant remove the last system admin!")
        }

        log.info("User deleted: {}", user.id)
        userRepository.deleteById(id);
        return true;
    }

    fun addRole(id:Long, roleName:String):Boolean{
        val user:User = findByIdOrThrow(id)
        if(user.roles.any{it.name==roleName}) return false;

        val role = roleRepository.findByName(roleName)?:throw BadRequestException("Role $roleName not found")

        user.roles.add(role)
        userRepository.save(user)
        log.info("Granted role {} to user {}", role.name, user.id)

        return true;
    }
    fun login(email:String,password:String):LoginResponse?{
        val user = userRepository.findByEmail(email)
        if(user == null){
            log.warn("User {} not found",email)
            return null
        }

        if(password!=user.password){
            log.warn("User {} not found",password)
        }
        log.info("User id={} email={} logged in",user.id,user.email)
        return LoginResponse(
            token = jwt.createToken(user),
            UserResponse(user)
        )
    }

    companion object{
        val log = LoggerFactory.getLogger(UserService::class.java)
    }
}
