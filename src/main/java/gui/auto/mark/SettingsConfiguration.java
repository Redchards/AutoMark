package gui.auto.mark;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SettingsConfiguration implements Iterable<Entry<String, String>> {
	Map<String, String> settingsMap;
	
	public SettingsConfiguration() {
		settingsMap = new LinkedHashMap<>();
	}
	
	public SettingsConfiguration(LinkedHashMap<String, String> settingsMap) {
		this.settingsMap = new LinkedHashMap<>(settingsMap);
	}
	
	/*
	 * TODO : verify that the settings does not already exist, so we do not override it.
	 */
	public void addSettings(String name, String fxmlFileName) {
		settingsMap.put(name, fxmlFileName);
	}
	
	
	public void removeSettings(String name) {
		settingsMap.remove(name);
	}
	
	public String getSettingsFXMLFile(String name) {
		return settingsMap.get(name);
	}
	
	public Collection<String> getAllSettingsFXMLFile() {
		return settingsMap.values();
	}
	
	public Set<String> getAllSettingsNames() {
		return settingsMap.keySet();
	}

	@Override
	public Iterator<Entry<String, String>> iterator() {
		return settingsMap.entrySet().iterator();
	}
	
	
}
