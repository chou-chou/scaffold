package com.hrp.utils.spring;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Date;

import com.hrp.utils.GsonUtil;
import com.hrp.utils.constants.ConstantDateFormatTypes;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.hrp.utils.io.JsonUtil;

/**
 * @ClassName:		SpringMessageConverterFastJson
 * @Description:	Spring MVC 整合阿里的fastjson
 * 					<p>
 * 					[依赖spring，fastjson.jar]
 * 					</p>
 * @Author:    		kevin
 * @CreateDte:		2016年7月17日 下午5:27:33
 *
 */
public class SpringMessageConverterFastJson extends AbstractHttpMessageConverter<Object> {
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	private SerializerFeature[] serializerFeature;

	public SpringMessageConverterFastJson() {
		super(new MediaType("application", "json", DEFAULT_CHARSET));
	}

	public SerializerFeature[] getSerializerFeature() {
		return serializerFeature;
	}

	/**
	 * setSerializerFeature
	 */
	public void setSerializerFeature(final SerializerFeature[] serializerFeatureP) {
		this.serializerFeature = serializerFeatureP;
	}

	/**
	 * canRead
	 */
	public boolean canRead(final Class<?> clazz, final MediaType mediaType) {
		return true;
	}

	/**
	 * canWrite
	 */
	public boolean canWrite(final Class<?> clazz, final MediaType mediaType) {
		return true;
	}

	@Override
	protected boolean supports(final Class<?> clazz) {
		throw new UnsupportedOperationException();
	}

	/**
	 * readInternal
	 */
	@Override
	protected Object readInternal(final Class<? extends Object> clazz, final HttpInputMessage inputMessage) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i;
		while ((i = inputMessage.getBody().read()) != -1) {
			baos.write(i);
		}

		return JsonUtil.json2List(baos.toString(), clazz);
	}

	/**
	 * writeInternal
	 */
	@Override
	protected void writeInternal(final Object o, final HttpOutputMessage outputMessage) throws IOException {
		SerializeConfig sc = new SerializeConfig();
		sc.put(Date.class, new SimpleDateFormatSerializer(ConstantDateFormatTypes.YYYY_MM_DD_HH24_MM_SS));
		sc.put(java.sql.Date.class, new SimpleDateFormatSerializer(ConstantDateFormatTypes.YYYY_MM_DD_HH24_MM_SS));
		sc.put(Timestamp.class, new SimpleDateFormatSerializer(ConstantDateFormatTypes.YYYY_MM_DD_HH24_MM_SS));
		String jsonString = JSON.toJSONString(o, sc, serializerFeature);
		OutputStream out = outputMessage.getBody();
		out.write(jsonString.getBytes(DEFAULT_CHARSET));
		out.flush();
	}
}