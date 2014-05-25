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

import org.jibble.pircbot.Colors;
import org.jibble.pircbot.PircBot;

/**
 * 
 * @author ghostnew
 * @version 1.0
 */

public class XDCCBot extends PircBot {
	
	private int DCC_TimeOut = 12000;
	
	public XDCCBot()
	{
		setName(XDCCMain.IRCnick);
	    setLogin("Bot");
	    setAutoNickChange(true);
	    setVerbose(XDCCMain.debug);
	 }
	
	public void onPrivateMessage( String sender, String login, String hostname, String message){
		if(message.toLowerCase().startsWith("xdcc")){
			String args[] = message.split(" ");
			if(args.length == 2){
				if(args[1].toLowerCase().equals("list"))
					getXDCCList(sender);
			}else if(args.length == 3){
				if(args[1].toLowerCase().equals("send")){
					String fileToGet = "Public/" + args[2];
					dccSendFile(new File(fileToGet), sender, DCC_TimeOut);
				}
			}
			
		}
	}

	private void getXDCCList(String sender) {
		File dir = new File("./Public/");
		File[] files = dir.listFiles( );
        if (files == null || files.length == 0) {
        	sendMessage(sender, Colors.BOLD + Colors.RED + "Sorry, no files available for the moment");
        } else {
            for (int i = 0; i < files.length; i++) {
                if(files[i].isFile()) { 
                    sendMessage(sender, files[i].getName( ));
                }
            }
        }
	}
}