package com.sgq.tool.sync.util;

import java.util.Random;

/**
 * Keep updating console output with latest status
 */
public final class ConsoleStatusPrinter
{
	private int maxPrintedLength;

	public ConsoleStatusPrinter()
	{
		this.maxPrintedLength = 0;
	}

	public static void main(final String[] args) throws InterruptedException
	{
		final Random random = new Random();
		final ConsoleStatusPrinter printer = new ConsoleStatusPrinter();
		for (int i = 0; i < 20; i++)
		{
			Thread.sleep(1000);
			final int length = random.nextInt(10) + 10;
			final StringBuilder builder = new StringBuilder();
			for (int j = 0; j < length; j++) builder.append(random.nextInt(10));
			builder.append(" [").append(i).append("]");
			printer.print(builder.toString());
		}
	}

	public void print(final String newStatus)
	{
		System.out.print('\r');
		System.out.print(newStatus);
		final int padCount = maxPrintedLength - newStatus.length();
		if (padCount <= 0) return;
		maxPrintedLength = newStatus.length();
		for (int i = 0; i < padCount; i++) System.out.print(" ");
	}
}
