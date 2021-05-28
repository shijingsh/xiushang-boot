package com.mg.framework.sys;

import org.springframework.http.HttpOutputMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class StringHttpMessageConverter extends org.springframework.http.converter.StringHttpMessageConverter {

	public final static Charset UTF8 = Charset.forName("UTF-8");

	public static final int BUFFER_SIZE = 4096;

	public StringHttpMessageConverter() {
		super(UTF8);
	}

	public StringHttpMessageConverter(Charset defaultCharset) {
		super(UTF8);
	}

	@Override
	protected void writeInternal(String str, HttpOutputMessage outputMessage) throws IOException {

		outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());

		OutputStream out = outputMessage.getBody();
		byte[] bytes = str.getBytes(UTF8);

		int length = bytes.length;

		for (int i = 0; i < length; i++) {
			byte b = bytes[i];
			out.write(b);
		}

		// int i = 0;
		// while (i < length) {
		//
		// if (i + 512 < length) {
		// out.write(bytes, i, 512);
		// } else {
		// out.write(bytes, i, length - i);
		// }
		// i = i + 512;
		// }

		out.flush();

	}
}
