export class DataService {

	private static localURL:string  = "http://localhost:8080";
	private static remoteServerURL:string = "https://thawing-thicket-56905.herokuapp.com";
	static BACKEND_URL:string = DataService.localURL;

	static FILES_URL:string = "https://s3.eu-central-1.amazonaws.com/dzmitryf.portfolio";
}
