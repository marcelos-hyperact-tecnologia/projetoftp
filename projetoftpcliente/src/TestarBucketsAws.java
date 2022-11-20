import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;

import br.com.marcelos.bucket.aws.BucketXp;

public class TestarBucketsAws {

	public static void main(String args[]) {

		BucketXp bucketsxp = new BucketXp();
		AmazonS3 cliente = bucketsxp.clienteS3();

		// pegando a lista de buckets ...
		List<Bucket> lista = cliente.listBuckets();

		for (Bucket obj : lista) {
			System.out.println(obj.getName());
		}
	}

}
