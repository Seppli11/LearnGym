package ninja.seppli.learngym.saveload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Saves a LearnGymModel object
 *
 * @author sebi
 *
 */
public class JaxbLoader {

	/**
	 * loads a {@link CourseModel}
	 *
	 * @param in the input stream
	 * @return the loaded course model
	 * @throws JAXBException
	 */
	public CourseModel load(InputStream in) throws JAXBException {
		JAXBContext ctx = JAXBContext.newInstance(CourseModel.class);
		Unmarshaller mar = ctx.createUnmarshaller();
		return (CourseModel) mar.unmarshal(in);
	}

	/**
	 * loads a {@link CourseModel}
	 *
	 * @param f the input file
	 * @return the loaded course model
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	public CourseModel load(File f) throws FileNotFoundException, JAXBException {
		return load(new FileInputStream(f));
	}
}
