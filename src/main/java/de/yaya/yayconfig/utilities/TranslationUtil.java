package de.yaya.yayconfig.utilities;

public class TranslationUtil
{
	public static String getTranslationKey(String key, String id)
	{
		return key + ".yayconfig." + id;
	}
}
