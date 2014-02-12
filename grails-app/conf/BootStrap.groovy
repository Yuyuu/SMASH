import com.smash.BootstrapService

class BootStrap {

    BootstrapService bootstrapService

    def init = { servletContext ->
        bootstrapService.initializeRoles()
        bootstrapService.inializeDevUsers()
    }

    def destroy = {
    }
}
