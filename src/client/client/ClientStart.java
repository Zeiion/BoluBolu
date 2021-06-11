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
		System.out.println(
			"─██████████████───██████████████─██████─────────██████──██████──██████████████───██████████████─██████─────────██████──██████─\n"
				+ "─██          ██───██          ██─██  ██─────────██  ██──██  ██──██          ██───██          ██─██  ██─────────██  ██──██  ██─\n"
				+ "─██  ██████  ██───██  ██████  ██─██  ██─────────██  ██──██  ██──██  ██████  ██───██  ██████  ██─██  ██─────────██  ██──██  ██─\n"
				+ "─██  ██──██  ██───██  ██──██  ██─██  ██─────────██  ██──██  ██──██  ██──██  ██───██  ██──██  ██─██  ██─────────██  ██──██  ██─\n"
				+ "─██  ██████  ████─██  ██──██  ██─██  ██─────────██  ██──██  ██──██  ██████  ████─██  ██──██  ██─██  ██─────────██  ██──██  ██─\n"
				+ "─██            ██─██  ██──██  ██─██  ██─────────██  ██──██  ██──██            ██─██  ██──██  ██─██  ██─────────██  ██──██  ██─\n"
				+ "─██  ████████  ██─██  ██──██  ██─██  ██─────────██  ██──██  ██──██  ████████  ██─██  ██──██  ██─██  ██─────────██  ██──██  ██─\n"
				+ "─██  ██────██  ██─██  ██──██  ██─██  ██─────────██  ██──██  ██──██  ██────██  ██─██  ██──██  ██─██  ██─────────██  ██──██  ██─\n"
				+ "─██  ████████  ██─██  ██████  ██─██  ██████████─██  ██████  ██──██  ████████  ██─██  ██████  ██─██  ██████████─██  ██████  ██─\n"
				+ "─██            ██─██          ██─██          ██─██          ██──██            ██─██          ██─██          ██─██          ██─\n"
				+ "─████████████████─██████████████─██████████████─██████████████──████████████████─██████████████─██████████████─██████████████─\n"
				+ "──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.println("请先配置服务器信息！\n" + "——————————————\n" + "输入指令介绍ヽ(･ω･´ﾒ)：：\n" + "new client --- 以当前配置启动客户端\n"
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
