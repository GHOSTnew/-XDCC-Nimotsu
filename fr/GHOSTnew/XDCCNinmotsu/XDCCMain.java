/*  Copyright GHOSTnew 2014
 * 
 *  This file is part of [XDCC]Nimotsu.
 *  
 *  [XDCC]Nimotsu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  [XDCC]Nimotsu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with [XDCC]Nimotsu.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.GHOSTnew.XDCCNinmotsu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

/**
 * 
 * @author ghostnew
 * @version 1.0
 *
 */


public class XDCCMain {
	
	private static String IRCchan = "#mychan";
	private static String IRCserver = "irc.myserv.net";
	private static int IRCport = 6667;
	private static String IRCpassword = "";
	public static String IRCnick = "[XDCC]Nimotsu";
	public static boolean debug = false;
	
	public static void main(String args[]) throws NickAlreadyInUseException, IOException, IrcException{
		Properties prop = new Properties();
		InputStream input = null;
		OutputStream output = null;
	 
		try {
	 
			input = new FileInputStream("config.properties");
			prop.load(input);
			IRCserver = prop.getProperty("server");
			IRCpassword = prop.getProperty("password");
			IRCport = Integer.parseInt(prop.getProperty("port"));
			IRCchan = prop.getProperty("channel");
			IRCnick = prop.getProperty("nick");
			debug = Boolean.parseBoolean(prop.getProperty("debug"));
	 
		} catch (FileNotFoundException ex) {
			try {
				 
				output = new FileOutputStream("config.properties");
				prop.setProperty("server", IRCserver);
				prop.setProperty("password", IRCpassword);
				prop.setProperty("channel", IRCchan);
				prop.setProperty("port", String.valueOf(IRCport));
				prop.setProperty("nick", IRCnick);
				prop.setProperty("debug", String.valueOf(debug));
				prop.store(output, null);
		 
			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		 
			}
			System.err.println("You need to edit the config.properties");
			System.exit(1);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		File dir = new File("./Public/");
		if(!dir.exists() && !dir.isDirectory()){
			dir.mkdir();
		}
		XDCCBot bot = new XDCCBot();
		if(IRCpassword != "" && IRCpassword != null){
			bot.connect(IRCserver, IRCport, IRCpassword);
		}else {
			bot.connect(IRCserver, IRCport);
		}
		bot.joinChannel(IRCchan);
	}
}