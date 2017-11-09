import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;

public class InvoiceInsert {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		String url = "http://localhost:9999/invoiceIntegration/invoices/v1/";

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);

		/*
		 * Charset - Deve ser UTF-8
		 * contentType - ContentType.MULTIPART_FORM_DATA
		 * boundary - Um identificador unico para a requisição
		 */
		String charset = "UTF-8";
		ContentType contentType = ContentType.MULTIPART_FORM_DATA.withCharset(Charset.forName(charset));
		String boundary = UUID.randomUUID().toString();

		MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();

		/*
		 * FILES
		 * - toda vez que precisar enviar um arquivo deve ser utilizado o método addPart. 
		 * 		Pode ser enviado mais de um arquivo por requisição. Basta adicionar um novo addPart.
		 * 
		 * contentType - ContentType.MULTIPART_FORM_DATA.withCharset(Charset.forName(charset))
		 * file_name 		 - Aqui é o nome do arquivo, este nome não deve conter caractere especial
		 * 
		 * addPart
		 * 		FileBody (File, contentType, file_name)
		 * 		ou
		 * 		InputStreamBody (InputStream, contentType, file_name)
		 * 
		 * */
		File arquivo = new File("c:/srm-config/queue.properties");
		multipartEntity.addPart("file_name", new FileBody(arquivo, contentType, arquivo.getName()));
		/*
		 * contentType - ContentType.MULTIPART_FORM_DATA.withCharset(Charset.forName(charset))
		 * charset - Deve ser UTF-8
		 * boundary - Um identificador unico para a requisição
		 */
		multipartEntity.setContentType(contentType);
		multipartEntity.setCharset(Charset.forName(charset));
		multipartEntity.setBoundary(boundary);

		request.setEntity(multipartEntity.build());

		/*
		 * Esta é a base de Cabecalho que precisa ser implementado
		 * boundary 		- Um identificador unico para a requisição, deve ser o mesmo utilizado no arquivo (multipartEntity.boundary)
		 * charset 			- UTF-8
		 * Content-Type 	- É necessário que contenha: multipart/form-data;boundary=;charset=
		 * Accept 			- multipart/form-data
		 * enctype 			- multipart/form-data
		 * Authorization 	- É neste parametro que será enviado o token de autorização do parceiro. Sempre deve conter o prefixo Bear 
		 * */
		request.addHeader("charset", charset);
		request.addHeader("Content-Type", contentType.getMimeType() + ";boundary=" + boundary + "; charset=" + charset);
		request.addHeader("Accept", contentType.getMimeType());
		request.addHeader("enctype", contentType.getMimeType());
		request.addHeader("Authorization", "Bearer " + "99f0e2361ccbf5dca644e78ba6038316");

		/*
		 * Este é o método a ser executado
		 * */
		HttpResponse response = client.execute(request);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
	}
}
