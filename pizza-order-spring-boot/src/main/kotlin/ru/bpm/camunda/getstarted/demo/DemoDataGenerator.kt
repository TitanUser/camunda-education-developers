package ru.bpm.camunda.getstarted.demo

import org.camunda.bpm.engine.AuthorizationService
import org.camunda.bpm.engine.FilterService
import org.camunda.bpm.engine.IdentityService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.authorization.Authorization
import org.camunda.bpm.engine.authorization.Authorization.AUTH_TYPE_GRANT
import org.camunda.bpm.engine.authorization.Groups
import org.camunda.bpm.engine.authorization.Permissions
import org.camunda.bpm.engine.authorization.Resources
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

@Component
class DemoDataGenerator(
        private val identityService: IdentityService,
        private val authorizationService: AuthorizationService,
        private val filterService: FilterService,
        private val taskService: TaskService
) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        createUsers()
        createGroups()
        createGroupMembership()
        createGroupAuth()
        createFilter()
    }

    fun createUsers() {

        if (identityService.isReadOnly) {
            LOGGER.info("Identity service provider is Read Only, not creating any demo users.")
            return
        }

        LOGGER.info("Generating demo data for invoice showcase")

        // create Users

        val deliveryBoy = identityService.newUser("deliveryBoy")
        deliveryBoy.firstName = "Delivery"
        deliveryBoy.lastName = "Boy"
        deliveryBoy.password = "deliveryBoy"
        deliveryBoy.email = "deliveryBoy@test.ru"
        identityService.saveUser(deliveryBoy)

        val superCourier = identityService.newUser("superCourier")
        superCourier.firstName = "Super"
        superCourier.lastName = "Courier"
        superCourier.password = "superCourier"
        superCourier.email = "superCourier@test.ru"
        identityService.saveUser(superCourier)

        val callCenter1 = identityService.newUser("callCenter1")
        callCenter1.firstName = "Call"
        callCenter1.lastName = "Center"
        callCenter1.password = "callCenter1"
        callCenter1.email = "callCenter1@test.ru"
        identityService.saveUser(callCenter1)

        val callCenter2 = identityService.newUser("callCenter2")
        callCenter2.firstName = "Call"
        callCenter2.lastName = "Center2"
        callCenter2.password = "callCenter2"
        callCenter2.email = "callCenter2@test.ru"
        identityService.saveUser(callCenter2)

        val manager = identityService.newUser("manager")
        manager.firstName = "Manager"
        manager.lastName = "User"
        manager.password = "manager"
        manager.email = "manager@test.ru"
        identityService.saveUser(manager)
    }

    fun createGroups() {
        val callCenterGroup = identityService.newGroup("CallCenterGroup")
        callCenterGroup.name = "CallCenterGroup"
        callCenterGroup.type = "WORKFLOW"
        identityService.saveGroup(callCenterGroup)

        val managerGroup = identityService.newGroup("ManagerGroup")
        managerGroup.name = "ManagerGroup"
        managerGroup.type = "WORKFLOW"
        identityService.saveGroup(managerGroup)

        val deliveryGroup = identityService.newGroup("DeliveryGroup")
        deliveryGroup.name = "DeliveryGroup"
        deliveryGroup.type = "WORKFLOW"
        identityService.saveGroup(deliveryGroup)


        // create admin group
        if (identityService.createGroupQuery().groupId(Groups.CAMUNDA_ADMIN).count() == 0L) {
            val camundaAdminGroup = identityService.newGroup(Groups.CAMUNDA_ADMIN)
            camundaAdminGroup.name = "Camunda BPM Administrators"
            camundaAdminGroup.type = Groups.GROUP_TYPE_SYSTEM
            identityService.saveGroup(camundaAdminGroup)
        }
    }

    fun createGroupMembership() {
        identityService.createMembership("callCenter1", "CallCenterGroup")
        identityService.createMembership("callCenter2", "CallCenterGroup")
        identityService.createMembership("manager", "ManagerGroup")
        identityService.createMembership("deliveryBoy", "DeliveryGroup")
        identityService.createMembership("superCourier", "DeliveryGroup")
    }

    fun createGroupAuth() {
        addGroupAuth("CallCenterGroup")
        addGroupAuth("ManagerGroup")
        addGroupAuth("DeliveryGroup")
    }

    fun createFilter() {
        val filterProperties = HashMap<String, Any>()
        filterProperties["description"] = "Все задачи"
        filterProperties["priority"] = -10
        filterProperties["color"] = "#555555"
        filterProperties["showUndefinedVariable"] = false
        filterProperties["refresh"] = true

        if (filterService.createFilterQuery().filterName("Все задачи").singleResult() == null) {
            val filter = filterService.newTaskFilter().setName("Все задачи").setProperties(filterProperties).setQuery(taskService.createTaskQuery())
            filterService.saveFilter(filter)
            addGroupAuth(AUTH_TYPE_GRANT, "CallCenterGroup", Resources.FILTER, filter.id, Permissions.READ)
            addGroupAuth(AUTH_TYPE_GRANT, "ManagerGroup", Resources.FILTER, filter.id, Permissions.READ)
            addGroupAuth(AUTH_TYPE_GRANT, "DeliveryGroup", Resources.FILTER, filter.id, Permissions.READ)
        }
    }

    fun addGroupAuth(groupName: String) {
        addGroupAuth(
                AUTH_TYPE_GRANT, groupName, Resources.APPLICATION, "welcome",
                Permissions.ACCESS
        )
        addGroupAuth(
                AUTH_TYPE_GRANT, groupName, Resources.APPLICATION, "tasklist",
                Permissions.ACCESS
        )
        addGroupAuth(
                AUTH_TYPE_GRANT, groupName, Resources.PROCESS_DEFINITION, "*",
//                Permissions.CREATE_INSTANCE,
                Permissions.READ,
//                Permissions.READ_INSTANCE,
                Permissions.READ_HISTORY,
//                Permissions.UPDATE_INSTANCE,
                Permissions.UPDATE
        )
        addGroupAuth(
                AUTH_TYPE_GRANT, groupName, Resources.PROCESS_INSTANCE, "*",
                Permissions.CREATE,
                Permissions.READ,
                Permissions.UPDATE_INSTANCE,
                Permissions.UPDATE
        )
        addGroupAuth(
                AUTH_TYPE_GRANT, groupName, Resources.TASK, "*",
                Permissions.READ,
                Permissions.UPDATE
        )
        addGroupAuth(
                AUTH_TYPE_GRANT, groupName, Resources.GROUP, groupName,
                Permissions.READ
        )
    }

    private fun groupAuthExist(group: String, resource: Resources, resourceId: String): Authorization? {
        return authorizationService.createAuthorizationQuery().groupIdIn(group).resourceType(resource)
                .resourceId(resourceId).singleResult()
    }

    private fun addGroupAuth(
            authType: Int, group: String, resource: Resources, resourceId: String, vararg
            permissions: Permissions
    ) {
        groupAuthExist(group, resource, resourceId)
                ?: authorizationService.saveAuthorization(authorizationService
                        .createNewAuthorization(authType).apply {
                            groupId = group
                            setResource(resource)
                            this.resourceId = resourceId
                            permissions.forEach { addPermission(it) }
                        })
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(DemoDataGenerator::class.java)
        const val PROCESS_PIZZA_ID = "PizzaRequestProcessId"
        const val TASKLIST_RESOURCE_ID = "tasklist"
    }
}