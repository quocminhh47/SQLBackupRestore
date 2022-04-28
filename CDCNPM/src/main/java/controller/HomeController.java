package controller;


import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dao.DAO;
import model.BackupInfo;

@Controller
public class HomeController {
	
	DAO dao = new DAO();
	String user="";
	String password = "";
	
	String mess="";
	
	public Boolean isDevExist(String dbName, ModelMap mm) {
		boolean rs = false;
		List<String> listDev = new ArrayList<String>();
			listDev =	dao.listDev(user, password);
		if(listDev != null ) {
			String dbDev = "DEV_"+dbName.replaceAll(" ", "");		
			for(String s : listDev) {
				if(dbDev.equals(s)) {
					rs = true;
					mm.addAttribute("dropBackupDEV", "  DROP DEVICE BACKUP");
					mm.addAttribute("dropBackupDEV2", "  DROP DEVICE BACKUP WITH DELETING THE BACKUP FILE");
					System.out.println(dbName + "\t" + 1);
					break;
				}
				
			}
			if(rs == false) {
				mm.addAttribute("createBackupDEV", " CREATE DEVICE BACKUP");
			}
			
		}
		
		return rs;
		
	}
	
	@RequestMapping("/")
	public String start() {
		return "redirect:/login.htm";
	}
	
	@RequestMapping("/login.htm")
	public String login() {
		return "login2";
	}
	
	@RequestMapping(value = "/login.htm", method = RequestMethod.POST)
	public String getUser(@RequestParam("user") String username, @RequestParam("pass") String pass, ModelMap mm) {
		this.user = username;
		this.password = pass;
		if(dao.getConnection("master", username, pass) == null) {
			mm.addAttribute("mess", "LOGIN FAILED!!");
			return "login2";
		}
		else {
			List<String> dbNames = new ArrayList<String>();
			dbNames = dao.getListDB("master",user, password);
			mm.addAttribute("d", dbNames);
			return "redirect:home.htm";
		}
		
	}
	
	@RequestMapping("home.htm")
	public String home(ModelMap mm,  @ModelAttribute("mess") String mess) {
		if(dao.getConnection("master", user, password) == null) {
			return "redirect:/login.htm";
		}
		else {
			List<String> dbNames = new ArrayList<String>();
			dbNames = dao.getListDB("master",user, password);
			mm.addAttribute("d", dbNames);
			mm.addAttribute("mess", mess);
			return "dbInfo";
		}
		
	}
	
	public void getInfo(String dbName, ModelMap mm) {
		
		List<BackupInfo>list = new ArrayList<BackupInfo>();
		List<String> dbNames = new ArrayList<String>();
		dbNames = dao.getListDB("master",user, password);
		list = dao.getBackupList(dbName, user, password);
		mm.addAttribute("db", dbName);
			mm.addAttribute("list", list);
			mm.addAttribute("d", dbNames);

	}
	
	@RequestMapping("/backup-{name}.htm")
	public String backup(@PathVariable("name") String dbName, ModelMap mm,
						  @ModelAttribute("mess") String mess) {
		if(dao.getConnection("master", user, password) == null) {
			return "redirect:/login.htm";
		}
		else {
			mm.addAttribute("mess", mess);
			List<BackupInfo>list = new ArrayList<BackupInfo>();
			List<String> dbNames = new ArrayList<String>();
			getInfo(dbName, mm);
			boolean dev = isDevExist(dbName, mm);
			if (dev == true) {
				mm.addAttribute("dev", "DEV_"+dbName);
			}
			else {
				mm.addAttribute("dev", "NO BACKUP DEVICE CREATED!!");
			}
			return "backup";
		}
	
	}
	
	@RequestMapping("/backup.htm")
	public String backup2( ModelMap mm) {
		List<String> dbNames = new ArrayList<String>();
		 dbNames = dao.getListDB("master",user, password);
		mm.addAttribute("d", dbNames);
		return "backup";
	}
	
	
	
	@RequestMapping("/restore-{db}-{position}.htm")
	public String restoreDbFromPosition(@PathVariable("db") String db,@PathVariable("position") String position,
										ModelMap mm , RedirectAttributes redirectAttributes) {
		int rs = dao.restoreDbFromFile(db,user,password, position);
		if (rs == 1) {
			redirectAttributes.addFlashAttribute("mess", "Restore success!!");
		}
		else {
			redirectAttributes.addFlashAttribute("mess", "Restore failed!!, Make sure that the file is valid!!");
		}
		isDevExist(db, mm);
		getInfo(db, mm);
		boolean dev = isDevExist(db, mm);
		if (dev == true) {
			mm.addAttribute("dev", "DEV_"+db);
		}
		else {
			mm.addAttribute("dev", "NO BACKUP DEVICE CREATED!!");
		}
		return "redirect:/backup-{db}.htm";
	}
	
	@RequestMapping("create-backup-{db}.htm")
	public String createBackup(@PathVariable("db") String dbName, ModelMap mm, RedirectAttributes redirectAttributes) {
		int i = dao.createBackup(dbName, user, password);
		if( i == 1) {
			redirectAttributes.addFlashAttribute("mess", "CREATE BACKUP SUCCESS!!");
		}
		else {
			redirectAttributes.addFlashAttribute("mess", "CREATE BACKUP FAILED!!");
		}
		return "redirect:/backup-{db}.htm";
	}
	
	@RequestMapping("create-backup-{db}-init.htm")
	public String createBackup2(@PathVariable("db") String dbName, ModelMap mm, RedirectAttributes redirectAttributes) {
		int i = dao.createBackup2(dbName, user,password);
		if( i == 1) {
			redirectAttributes.addFlashAttribute("mess", "CREATE BACKUP SUCCESS!!");
		}
		else {
			redirectAttributes.addFlashAttribute("mess", "CREATE BACKUP FAILED!!");
		}
		boolean dev = isDevExist(dbName, mm);
		if (dev == true) {
			mm.addAttribute("dev", "DEV_"+dbName);
		}
		else {
			mm.addAttribute("dev", "NO BACKUP DEVICE CREATED!!");
		}
		isDevExist(dbName, mm);
		getInfo(dbName, mm);
		return "redirect:/backup-{db}.htm";
	}
	
	@RequestMapping(value="restore-{db}-from-time.htm")
	public String restoreFromTime(@PathVariable("db") String dbName, ModelMap mm,
									HttpServletRequest req,  RedirectAttributes redirectAttributes) throws SQLException{									
		String date = req.getParameter("myDate");
		String time = req.getParameter("myTime");
		LocalTime localTime = LocalTime.parse(time);
		if(localTime.isAfter(LocalTime.now())) {
			redirectAttributes.addFlashAttribute("mess","Thời gian nhập vào không hợp lệ!!");
		}
		else {
			String dateTime = date + " " +time;
			int i = dao.restoreDbFromTime(dbName,user,password, dateTime);
			if (i != 0) {
				redirectAttributes.addFlashAttribute("mess", "Restore success!!");
			}
			else {
				redirectAttributes.addFlashAttribute("mess", "Restore failed!!, Make sure that the date-time is valid AND backup file is exist!!");
			}
		}
		return "redirect:/backup-{db}.htm";
	}
	
	@RequestMapping("/create-device-backup-{db}.htm")
	public String createDevBackup(@PathVariable("db") String dbName,
									ModelMap mm, RedirectAttributes redirectAttributes) {
		boolean isDevExist = isDevExist(dbName, mm);
		System.out.println( "DEV_" +dbName + isDevExist);
		if(isDevExist == true) {
			redirectAttributes.addFlashAttribute("mess", "BACUP DEVICE ALREADY EXIST!!");
		}
		else {
			int rs = dao.createDev(dbName, user, password);
			if(rs == 1) {
				redirectAttributes.addFlashAttribute("mess", "CREATE DEVICE BACKUP SUCCESS!!, NAME DEVICE: DEV_" + dbName);
			}
			else {
				redirectAttributes.addFlashAttribute("mess", "BACUP DEVICE ALREADY EXIST!! CREATE DEVICE BACKUP FAILED!!");
			}
			
		}
		return "redirect:/backup-{db}.htm";
	}
	
	@RequestMapping("/delete-device-backup-{db}.htm")
	public String deleteDevBackup(@PathVariable("db") String dbName,
									ModelMap mm,  RedirectAttributes redirectAttributes) {
		boolean isDevExist = isDevExist(dbName, mm);
		if(isDevExist == false) {
			redirectAttributes.addFlashAttribute("mess", "BACUP DEVICE IS NOT EXIST!!");
		}
		else {
			int rs = dao.dopDev(dbName, user, password);
			System.out.println(dbName + "DEVE: " + rs);
			if(rs == 1) {
				redirectAttributes.addFlashAttribute("mess", "DROP DEVICE BACKUP SUCCESS!!");
			}
			else {
				redirectAttributes.addFlashAttribute("mess", "DROP DEVICE BACKUP FAILED!!");
			}
			
		}
		return "redirect:/backup-{db}.htm";
	}
	
	@RequestMapping("/delete-log-file.htm")
	public String deleteLogFile( ModelMap mm,HttpServletRequest req,  RedirectAttributes redirectAttributes) {
			String date = req.getParameter("myDate2");
			int rs = dao.delDbLog( user, password, date);
			if(rs == 1) {
				redirectAttributes.addFlashAttribute("mess", "DELETE LOG FILE SUCCESS!!");
			}
			else {
				redirectAttributes.addFlashAttribute("mess", "DELETE LOG FILE FAILED!!");
		
	}
			return "redirect:/home.htm";
}
	@RequestMapping("/delete-device-backup-with-delete-{db}.htm")
	public String deleteBackupWithFile(@PathVariable("db") String dbName, ModelMap mm, RedirectAttributes redirectAttributes) {
		boolean isDevExist = isDevExist(dbName, mm);
		if(isDevExist == false) {
			redirectAttributes.addFlashAttribute("mess", "BACUP DEVICE IS NOT EXIST!!");
		}
		else {
			int rs = dao.dopDevWithDel(dbName, user, password);
			System.out.println(dbName + "DEVE: " + rs);
			if(rs == 1) {
				redirectAttributes.addFlashAttribute("mess", "DROP DEVICE BACKUP WITH DELETING FILE SUCCESS!!");
			}
			else {
				redirectAttributes.addFlashAttribute("mess", "DROP DEVICE BACKUP WITH DELETING FILE FAILED, MAKE SURE THAT FILE IS EXIST!!");
			}
			
		}
		return "redirect:/backup-{db}.htm";
	}
}
