package client.client;

import client.ui.LoginWindow;
import java.util.Scanner;
import server.config.ServerInfo;

/**
 * 客户端入口
 *
 * @author BoluBolu
 */
public class ClientStart {
	public static void main(String[] args) {
		System.out.println("请最大化以显示全貌！");
		System.out.println(
			"─OOOOOOOOOOOOOO───OOOOOOOOOOOOOO─OOOOOO─────────OOOOOO──OOOOOO──OOOOOOOOOOOOOO───OOOOOOOOOOOOOO─OOOOOO─────────OOOOOO──OOOOOO─\n"
				+ "─OO          OO───OO          OO─OO  OO─────────OO  OO──OO  OO──OO          OO───OO          OO─OO  OO─────────OO  OO──OO  OO─\n"
				+ "─OO  OOOOOO  OO───OO  OOOOOO  OO─OO  OO─────────OO  OO──OO  OO──OO  OOOOOO  OO───OO  OOOOOO  OO─OO  OO─────────OO  OO──OO  OO─\n"
				+ "─OO  OO──OO  OO───OO  OO──OO  OO─OO  OO─────────OO  OO──OO  OO──OO  OO──OO  OO───OO  OO──OO  OO─OO  OO─────────OO  OO──OO  OO─\n"
				+ "─OO  OOOOOO  OOOO─OO  OO──OO  OO─OO  OO─────────OO  OO──OO  OO──OO  OOOOOO  OOOO─OO  OO──OO  OO─OO  OO─────────OO  OO──OO  OO─\n"
				+ "─OO            OO─OO  OO──OO  OO─OO  OO─────────OO  OO──OO  OO──OO            OO─OO  OO──OO  OO─OO  OO─────────OO  OO──OO  OO─\n"
				+ "─OO  OOOOOOOO  OO─OO  OO──OO  OO─OO  OO─────────OO  OO──OO  OO──OO  OOOOOOOO  OO─OO  OO──OO  OO─OO  OO─────────OO  OO──OO  OO─\n"
				+ "─OO  OO────OO  OO─OO  OO──OO  OO─OO  OO─────────OO  OO──OO  OO──OO  OO────OO  OO─OO  OO──OO  OO─OO  OO─────────OO  OO──OO  OO─\n"
				+ "─OO  OOOOOOOO  OO─OO  OOOOOO  OO─OO  OOOOOOOOOO─OO  OOOOOO  OO──OO  OOOOOOOO  OO─OO  OOOOOO  OO─OO  OOOOOOOOOO─OO  OOOOOO  OO─\n"
				+ "─OO            OO─OO          OO─OO          OO─OO          OO──OO            OO─OO          OO─OO          OO─OO          OO─\n"
				+ "─OOOOOOOOOOOOOOOO─OOOOOOOOOOOOOO─OOOOOOOOOOOOOO─OOOOOOOOOOOOOO──OOOOOOOOOOOOOOOO─OOOOOOOOOOOOOO─OOOOOOOOOOOOOO─OOOOOOOOOOOOOO─\n"
				+ "──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.println(
			"请先配置服务器信息！\n(本地运行则无需更改)\n" + "——————————————\n" + "输入指令介绍ヽ(･ω･´ﾒ)：：\n" + "new client --- 以当前配置启动客户端\n"
				+ "change ip XXX --- 更改ip地址\n" + "——————————————");
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
				if ("client".equals(opt)) {
					new LoginWindow();
					continue;
				} else {
					System.out.println("Params' count illegal");
					continue;
				}
			}
			if ("change".equals(input[0])) {
				if (input.length != 3) {
					System.out.println("Params' count illegal");
					continue;
				}
				if (!"ip".equals(input[1])) {
					System.out.println("Command not exist");
					continue;
				}
				String newIP = input[2];
				ServerInfo.setServerIp(newIP);
				System.out.println("服务器地址更改为：" + ServerInfo.SERVER_IP);
				continue;
			}
			System.out.println("Command not exist");
			continue;
		}
	}
}
