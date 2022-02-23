package com.expressionparser;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("Expression Parser")
public interface ExpressionParserConfig extends Config
{
	@ConfigItem(
		keyName = "greeting",
		name = "On-Start message",
		description = "Display plugin description on client login"
	)
	default String greeting()
	{
		return "An expression parsing plugin.";
	}
}
