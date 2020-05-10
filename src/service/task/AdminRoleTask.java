package service.task;

import service.domain.Admin;
import service.domain.User;
import service.exceptions.PermissionException;

//This task will be called by the UI to promote or demote Users to Admins and vice versa
public class AdminRoleTask {
	private final AdminTask adminTask;
	private final UserTask userTask;
	
	//UI will call this default contructor
	public AdminRoleTask() {
		this(new AdminTask(), new UserTask());
	}

	//This constructor is used for JUNIT TEST ONLY where you can inject mocks for the tasks
	public AdminRoleTask(AdminTask adminTask, UserTask userTask) {
		this.adminTask = adminTask;
		this.userTask = userTask;
	}
	
	//this method promotes a user to admin
	public Admin PromoteUserToAdmin(User user) {
		User toPromote = this.userTask.findByEmail(user.getEmail());
		Admin promoted = adminTask.promote(toPromote);
		userTask.delete(toPromote.getEmail());
		return promoted;
	}
	
	//this method demotes an admin to a user
	public User DemoteAdminToUser(Admin admin) {
		Admin toDemote = this.adminTask.findBySchoolId(admin.getSchoolId());
		if (toDemote.isRootAdmin()) {
			throw new PermissionException("You cannot demote the root admin");
		}
		User demoted = userTask.demote(toDemote);
		adminTask.delete(toDemote.getSchoolId());
		return demoted;
	}
}
