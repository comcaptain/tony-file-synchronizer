package com.sgq.tool.sync;

import com.sgq.tool.util.TonyFileUtil;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;

public final class FileTransferClient implements Closeable
{
	private final Socket socket;

	public FileTransferClient(final SocketAddress socketAddress) throws IOException
	{
		final Socket socket = new Socket();
		socket.connect(socketAddress);
		this.socket = socket;
	}

	public static void main(final String[] args) throws IOException
	{
		final SocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 1234);
		try (final FileTransferClient client = new FileTransferClient(socketAddress))
		{
			client.transferFile(new File(
				"/Volumes/琅琊福地/Tony/video/video/动漫/【大王饶命】 EP01~12集 蓝光1080P合集 丨Your Majesty spare your life #动漫 #热血 #战斗 #玄幻 - YouTube.mkv"));
			client.transferFile(
				new File("/Volumes/外接盘/Frozen.2013.2160p.BluRay.x265.10bit.HDR.TrueHD.7.1.Atmos-SWTYBLZ.mkv"));
		}
	}

	public void transferFile(final File file) throws IOException
	{
		final long fileByteSize = file.length();
		final String fileName = file.getName();
		final byte[] fileNameBytes = fileName.getBytes(StandardCharsets.UTF_8);
		final DataOutputStream outputStream = new DataOutputStream(this.socket.getOutputStream());
		final byte[] buffer = new byte[2048];
		outputStream.writeInt(fileNameBytes.length);
		outputStream.write(fileNameBytes);
		outputStream.writeLong(fileByteSize);
		try (final BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file)))
		{
			long totalWrittenBytes = 0;
			int readBytes;
			final long bytesInOneGB = 1024L * 1024L * 1024L;
			while ((readBytes = reader.read(buffer)) > 0)
			{
				outputStream.write(buffer, 0, readBytes);
				totalWrittenBytes += readBytes;
				if (totalWrittenBytes % bytesInOneGB == 0)
				{
					System.out.println("Copied " + TonyFileUtil.getHumanReadableSize(totalWrittenBytes));
				}
			}
			System.out.println("Copied " + TonyFileUtil.getHumanReadableSize(totalWrittenBytes));
		}
	}

	@Override
	public void close() throws IOException
	{
		this.socket.close();
	}
}
