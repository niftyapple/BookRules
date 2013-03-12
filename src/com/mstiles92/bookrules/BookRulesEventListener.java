/**
 * This document is a part of the source code and related artifacts for
 * BookRules, an open source Bukkit plugin for automatically distributing
 * written books to new players.
 *
 * http://dev.bukkit.org/server-mods/bookrules/
 * http://github.com/mstiles92/BookRules
 *
 * Copyright � 2013 Matthew Stiles (mstiles92)
 *
 * Licensed under the Common Development and Distribution License Version 1.0
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the CDDL-1.0 License at
 * http://opensource.org/licenses/CDDL-1.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the license.
 */

package com.mstiles92.bookrules;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * PlayerJoinListener is a class that is used to detect when a player joins the
 * server and handle the event appropriately.
 * 
 * @author mstiles92
 */
public class BookRulesEventListener implements Listener {
	private final BookRulesPlugin plugin;
	private final String tag = ChatColor.BLUE + "[BookRules] " + ChatColor.GREEN;
	
	/**
	 * The main constructor of this class
	 * 
	 * @param plugin the instance of the plugin
	 */
	public BookRulesEventListener(BookRulesPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void OnPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
		if (plugin.updateAvailable && player.hasPermission("bookrules.receivealerts")) {
			player.sendMessage(tag + "New version available! See http://dev.bukkit.org/server-mods/bookrules/ for more information.");
			player.sendMessage(tag + "Current version: " + ChatColor.BLUE + plugin.getDescription().getVersion() + ChatColor.GREEN + ", New version: " + ChatColor.BLUE + plugin.latestKnownVersion);
			player.sendMessage(tag + "Changes in this version: " + ChatColor.BLUE + plugin.changes);
		}
		
		if (plugin.getConfig().getBoolean("Give-New-Books-On-Join")) {
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new GiveBookRunnable(plugin, player), plugin.getConfig().getLong("Seconds-Delay") * 20);
		}
	}
}