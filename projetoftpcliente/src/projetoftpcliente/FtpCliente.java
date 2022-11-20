package projetoftpcliente;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.amazonaws.services.s3.AmazonS3;

import br.com.marcelos.bucket.aws.BucketXp;

public class FtpCliente {

	public static void main(String[] args) throws SocketException, IOException {

		/*
		 * Configura��es para conectar ao FTP
		 * 
		 * 
		 */
		try {
			FTPClient ftp = new FTPClient();
			ftp.connect("44.194.86.148");
			ftp.login("ftpxpti", "xpti12345");
			ftp.changeWorkingDirectory("/");

			BucketXp bucket = new BucketXp();
			AmazonS3 ss3 = bucket.clienteS3();
			/* -------------------------------------------------------- */
			if (ftp.isConnected()) {
				FTPFile files[] = ftp.listDirectories();
				for (FTPFile a : files) {
					ftp.changeWorkingDirectory("/" + a.getName());
					acessarConteudoDiretorios(ftp, a, ss3, bucket);
				}
			} else {
				System.out.println("Não conectou no FTP");
			}
		} catch (IOException e) {
			e.getMessage();
		}
	}

	public static void acessarConteudoDiretorios(FTPClient cli, FTPFile f, AmazonS3 s3, BucketXp bucket)
			throws IOException {

		// alterando o diretorio::::
		FTPFile files[] = cli.listFiles();
		cli.setFileType(FTP.BINARY_FILE_TYPE);
		for (FTPFile arq : files) {
			// Download do arquivo do Ftp para a pasta Local.
			downloadArquivo(cli, arq);

			// enviar para o bucket:

			File fos = new File("c:/dados/" + arq.getName());
			bucket.uploadObjetosParaBucket(s3, "analitico-xpti", arq.getName(), fos);

			// Excluindo o arquivo do FTP após o download
			excluirArquivoServidorFtp(arq.getName(), cli);
		}
	}

	public static void downloadArquivo(FTPClient ftpClient, FTPFile ftpFile) throws IOException {
		// Download do arquivo do FTP ::::::
		FileOutputStream fos = new FileOutputStream("c:/dados/" + ftpFile.getName());
		ftpClient.retrieveFile(ftpFile.getName(), fos);
	}

	public static void excluirArquivoServidorFtp(String remoteFile, FTPClient ftpClient) throws IOException {
		ftpClient.enterLocalPassiveMode();
		boolean exist = ftpClient.deleteFile(remoteFile);
		if (exist) {
			System.out.println("Arquivo '" + remoteFile + "' excluído...");
		} else {
			System.out.println("Arquivo '" + remoteFile + "' não existe...");
		}
	}

}