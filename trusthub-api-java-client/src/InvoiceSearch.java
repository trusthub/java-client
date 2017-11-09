import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;

public class InvoiceSearch {

	private final static String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws ClientProtocolException, IOException {
		//String url = "http://localhost:9999/invoiceIntegration/invoices/v1/1";
		String url = "http://api.hom.trusthub.com.br/invoiceIntegration/invoices/v1/1";

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		/*
		 * Charset - Deve ser UTF-8
		 * contentType - ContentType.APPLICATION_JSON (application/json)
		 * boundary - Um identificador unico para a requisição
		 */
		String charset = "UTF-8";
		ContentType contentType = ContentType.APPLICATION_JSON.withCharset(Charset.forName(charset));
		String boundary = "---------------" + UUID.randomUUID().toString();

		/*
		 * Esta é a base de Cabecalho que precisa ser implementado
		 * boundary 		- Um identificador unico para a requisição, deve ser o mesmo utilizado no arquivo (multipartEntity.boundary)
		 * charset 			- UTF-8
		 * Content-Type 	- É necessário que contenha: application/json;boundary=;charset=
		 * Accept 			- application/json
		 * enctype 			- application/json
		 * Authorization 	- É neste parametro que será enviado o token de autorização do parceiro. Sempre deve conter o prefixo Bear 
		 * */
		request.addHeader("charset", charset);
		request.addHeader("Content-Type", contentType.getMimeType() + ";boundary=" + boundary + "; charset=" + charset);
		request.addHeader("Accept", contentType.getMimeType());
		request.addHeader("enctype", contentType.getMimeType());
		request.addHeader("Authorization", "Bearer " + "99f0e2361ccbf5dca644e78ba6038316");

		HttpResponse response = client.execute(request);
		response.addHeader("Content-Type", "application/json");

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
