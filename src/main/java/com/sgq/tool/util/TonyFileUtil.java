package com.sgq.tool.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public interface TonyFileUtil
{
	static String getHumanReadableSize(final long bytes)
	{
		final long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
		if (absB < 1024)
		{
			return bytes + " B";
		}
		long value = absB;
		final CharacterIterator ci = new StringCharacterIterator("KMGTPE");
		for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10)
		{
			value >>= 10;
			ci.next();
		}
		value *= Long.signum(bytes);
		return String.format("%.1f %cB", value / 1024.0, ci.current());
	}
}
