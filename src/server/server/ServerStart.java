package server.server;

import java.util.Scanner;
import server.config.ServerInfo;

/**
 * 服务端入口
 * <p>
 * 服务端启动类,建立服务器线程.
 *
 * @author BoluBolu
 */
public class ServerStart {
	public static void main(String[] args) {
		System.out.println(
			"─██████████████───██████████████─██████─────────██████──██████──██████████████───██████████████─██████─────────██████──██████─\n"
				+ "─██░░░░░░░░░░██───██░░░░░░░░░░██─██░░██─────────██░░██──██░░██──██░░░░░░░░░░██───██░░░░░░░░░░██─██░░██─────────██░░██──██░░██─\n"
				+ "─██░░██████░░██───██░░██████░░██─██░░██─────────██░░██──██░░██──██░░██████░░██───██░░██████░░██─██░░██─────────██░░██──██░░██─\n"
				+ "─██░░██──██░░██───██░░██──██░░██─██░░██─────────██░░██──██░░██──██░░██──██░░██───██░░██──██░░██─██░░██─────────██░░██──██░░██─\n"
				+ "─██░░██████░░████─██░░██──██░░██─██░░██─────────██░░██──██░░██──██░░██████░░████─██░░██──██░░██─██░░██─────────██░░██──██░░██─\n"
				+ "─██░░░░░░░░░░░░██─██░░██──██░░██─██░░██─────────██░░██──██░░██──██░░░░░░░░░░░░██─██░░██──██░░██─██░░██─────────██░░██──██░░██─\n"
				+ "─██░░████████░░██─██░░██──██░░██─██░░██─────────██░░██──██░░██──██░░████████░░██─██░░██──██░░██─██░░██─────────██░░██──██░░██─\n"
				+ "─██░░██────██░░██─██░░██──██░░██─██░░██─────────██░░██──██░░██──██░░██────██░░██─██░░██──██░░██─██░░██─────────██░░██──██░░██─\n"
				+ "─██░░████████░░██─██░░██████░░██─██░░██████████─██░░██████░░██──██░░████████░░██─██░░██████░░██─██░░██████████─██░░██████░░██─\n"
				+ "─██░░░░░░░░░░░░██─██░░░░░░░░░░██─██░░░░░░░░░░██─██░░░░░░░░░░██──██░░░░░░░░░░░░██─██░░░░░░░░░░██─██░░░░░░░░░░██─██░░░░░░░░░░██─\n"
				+ "─████████████████─██████████████─██████████████─██████████████──████████████████─██████████████─██████████████─██████████████─\n"
				+ "──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.println(
			"BoluBolu---请先配置数据库信息！\n" + "——————————————\n" + "输入指令介绍ヽ(･ω･´ﾒ)：\n" + "new server --- 以当前配置启动服务端\n"
				+ "change dbname XXX --- 更改登录的数据库名\n" + "change dbpwd XXX --- 更改登录的数据库密码\n" + "——————————————");
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String cmd = scanner.nextLine();
			String[] input = cmd.split("\\s+");
			if ("new".equals(input[0])) {
				if (input.length != 2) {
					System.out.println("Params' count illegal");
					continue;
				}
				String opt = input[1];
				if (opt.equals("server")) {
					new Thread(new ServerThread()).start();
					System.out.println("服务器地址为：" + ServerInfo.SERVER_IP);
					System.out.println("————服务器已成功开启————");
					continue;
				} else {
					System.out.println("Command illegal");
					continue;
				}
			}
			if ("change".equals(input[0])) {
				if (input.length != 3) {
					System.out.println("Params' count illegal");
					continue;
				}
				String opt = input[1];
				if ("dbname".equals(opt)) {
					ServerInfo.setDbUserName(input[2]);
					System.out.println("数据库用户名更改为：" + ServerInfo.DB_USER_NAME);
				} else if ("dbpwd".equals(opt)) {
					ServerInfo.setDbUserPassword(input[2]);
					System.out.println("数据库密码更改为：" + ServerInfo.DB_USER_PASSWORD);
				} else {
					System.out.println("Command illegal");
					continue;
				}
				continue;
			}
			System.out.println("Command illegal");
			continue;
		}
	}
}
