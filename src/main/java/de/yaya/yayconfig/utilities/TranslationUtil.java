package de.yaya.yayconfig.utilities;

public class TranslationUtil
{
	static String groupID = "yayconfig";
	
	public static void setActiveGroupID(String s)
	{
		groupID = s;
	}
	
	public static String getTranslationKey(String key, String id)
	{
		return key + "." + groupID + "." + id;
	}
}
