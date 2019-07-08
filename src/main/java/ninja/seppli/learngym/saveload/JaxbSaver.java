package ninja.seppli.learngym.saveload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Saves a LearnGymModel object
 *
 * @author sebi
 *
 */
public class JaxbSaver {
	/**
	 * logger
	 */
	private Logger logger = LogManager.getLogger();

	/**
	 * Saves a {@link CourseModel}
	 *
	 * @param out   the file where to save
	 * @param model the model to save
	 */
	public void save(OutputStream out, CourseModel model) {
		try {
			JAXBContext ctx = JAXBContext.newInstance(CourseModel.class);
			Marshaller mar = ctx.createMarshaller();
			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			mar.marshal(model, out);
		} catch (JAXBException e) {
			logger.error("Couldn't save " + e, e);
		}
	}

	/**
	 * Saves a {@link CourseModel}
	 *
	 * @param out   the file where to save
	 * @param model the model to save
	 * @throws FileNotFoundException
	 */
	public void save(File out, CourseModel model) throws FileNotFoundException {
		save(new FileOutputStream(out), model);
	}
}
