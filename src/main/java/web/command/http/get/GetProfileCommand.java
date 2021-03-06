package web.command.http.get;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import db.exception.AppException;
import db.exception.DBException;
import model.User;
import model.UserInfo;
import service.LoginService;
import service.ProfileService;
import web.Path;
import web.command.Command;
import web.command.CommandResult;
import web.command.http.HttpCommandResult;
import web.controller.RequestType;

public class GetProfileCommand implements Command{

	private static final Logger LOG = Logger.getLogger(GetRegistrationCommand.class);
		
	private LoginService loginService;
	private ProfileService profileService;
	
	public GetProfileCommand(LoginService loginService, ProfileService profileService) {
		this.loginService = loginService;
		this.profileService = profileService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
			throws DBException, AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession(true);

		User thisUser = (User) session.getAttribute("user");
		
		thisUser = loginService.findUserById(thisUser.getId());
		UserInfo thisUserInfo = profileService.findUserInfoById(thisUser.getInfoId());
		CommandResult cr = new HttpCommandResult(RequestType.GET,  Path.PAGE_PROFILE);
		session.setAttribute("thisUserInfo", thisUserInfo);
		session.setAttribute("user", thisUser);
		LOG.debug("Command finished");
		return cr;
	}

}
