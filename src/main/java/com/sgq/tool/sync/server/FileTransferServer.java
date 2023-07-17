package com.sgq.tool.sync.server;

import com.sgq.tool.sync.util.TonyFileUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public final class FileTransferServer implements Closeable
{
	private final ServerSocket socket;

	public FileTransferServer(final int port) throws IOException
	{
		this.socket = new ServerSocket(port);
	}

	public static void main(final String[] args) throws InterruptedException
	{
		for (int i = 0; i < 100; i++)
		{
			Thread.sleep(100);
			System.out.print("\33[2K\rText" + i);
		}
	}

	public static void abc(final String[] args) throws IOException
	{
		try (final FileTransferServer fileTransferServer = new FileTransferServer(1234))
		{
			fileTransferServer.listenToNewConnection();
		}
	}

	public void listenToNewConnection() throws IOException
	{
		while (true)
		{
			final Socket clientSocket = socket.accept();
			System.out.println("New connection from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
			final DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
			while (true)
			{
				System.out.println("Waiting for files...");
				final int fileNameSize = inputStream.readInt();
				final byte[] fileNameBytes = new byte[fileNameSize];
				inputStream.readFully(fileNameBytes);
				final String fileName = new String(fileNameBytes, StandardCharsets.UTF_8);
				System.out.println("Receiving file " + fileName);
				final long fileSize = inputStream.readLong();
				System.out.println("To-be-received file size is " + TonyFileUtil.getHumanReadableSize(fileSize));
				try (final BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(fileName)))
				{
					final long bytesInOneGB = 1024L * 1024L * 1024L;
					final byte[] fileBuffer = new byte[2048];
					long receivedBytes = 0;
					while (receivedBytes < fileSize)
					{
						final long remainingBytes = fileSize - receivedBytes;
						final int readBytes = inputStream.read(fileBuffer, 0,
							remainingBytes < fileBuffer.length ? (int) remainingBytes : fileBuffer.length);
						receivedBytes += readBytes;
						output.write(fileBuffer, 0, readBytes);
						if (receivedBytes % bytesInOneGB == 0)
						{
							System.out.println(
								"Copied " + TonyFileUtil.getHumanReadableSize(receivedBytes) + ", remaining " +
									TonyFileUtil.getHumanReadableSize(remainingBytes));
						}
					}
					System.out.println("Copied " + TonyFileUtil.getHumanReadableSize(receivedBytes));
				}
			}
		}
	}

	@Override
	public void close() throws IOException
	{
		this.socket.close();
	}
}
