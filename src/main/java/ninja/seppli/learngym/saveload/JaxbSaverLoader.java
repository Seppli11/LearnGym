package ninja.seppli.learngym.saveload;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Saves and loads a LearnGymModel object
 *
 * @author sebi
 *
 */
public class JaxbSaverLoader {
	private Logger logger = LogManager.getLogger();

	/**
	 * Saves a {@link CourseModel}
	 *
	 * @param file  the file where to save
	 * @param model the model to save
	 */
	public void save(File file, CourseModel model) {
		try {
			JAXBContext ctx = JAXBContext.newInstance(CourseModel.class);
			Marshaller mar = ctx.createMarshaller();
			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			mar.marshal(model, file);
		} catch (JAXBException e) {
			logger.error("Couldn't save " + e, e);
		}
	}
}
