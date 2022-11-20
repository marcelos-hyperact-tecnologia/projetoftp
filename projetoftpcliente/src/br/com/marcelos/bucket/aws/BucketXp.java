package br.com.marcelos.bucket.aws;

import java.io.File;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

public class BucketXp {

	// credenciais da AWS 3
	AWSCredentials credentials = new BasicAWSCredentials("AKIA5I4YEUL4ELLGN47S",
			"v3IJxh4ISHbxtqA38OWg6TVb/648wLebT+DLvo9L");

	public BucketXp() {

	}

	public AmazonS3 clienteS3() {
		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1).build();
		return s3client;
	}

	public List<Bucket> listaDeBuckets() {
		List<Bucket> buckets = this.clienteS3().listBuckets();
		return buckets;
	}

	public void uploadObjetosParaBucket(AmazonS3 cli, String bucketNome, String arquivoCriar, File ArquivoLocal) {

		/*
		 * TODO: adicionar na versão final o nome do cliente e as subpastas das imagens
		 * de cada câmera
		 * 
		 */

		// Pasta no Bucket S3 em que ficarão armazenadas as imagens.
		String destinationPath = "img/cliente1/0002_LPR/" + ArquivoLocal.getName();
		// Cria o objeto para ser enviado ao bucket ...
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketNome, destinationPath, ArquivoLocal);
		// envia e retorna o resultado do upload do arquivo para o bucket.
		PutObjectResult putObjectResult = cli.putObject(putObjectRequest);
		cli.putObject(bucketNome, arquivoCriar, ArquivoLocal);

	}

}
