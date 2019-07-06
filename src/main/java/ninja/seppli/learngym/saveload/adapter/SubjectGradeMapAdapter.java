package ninja.seppli.learngym.saveload.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import ninja.seppli.learngym.model.Student;
import ninja.seppli.learngym.saveload.adapter.SubjectGradeMapAdapter.MapEntryType;

/**
 * An adpater to adapt between {@link Map} of {@link Student} to {@link Double}
 * and a List so it can be serialized with jaxb
 *
 * @author sebi
 *
 */
public class SubjectGradeMapAdapter extends XmlAdapter<MapEntryType[], Map<Student, Double>> {

	@Override
	public Map<Student, Double> unmarshal(MapEntryType[] list) throws Exception {
		Map<Student, Double> map = new HashMap<>();
		for (MapEntryType entry : list) {
			map.put(entry.key, entry.value);
		}
		return map;
	}

	@Override
	public MapEntryType[] marshal(Map<Student, Double> v) throws Exception {
		List<MapEntryType> list = new ArrayList<>();
		for (Entry<Student, Double> e : v.entrySet()) {
			list.add(new MapEntryType(e.getKey(), e.getValue()));
		}
		return list.stream().toArray(MapEntryType[]::new);
	}

	/**
	 * An entry for the list/array.
	 *
	 * @author sebi
	 *
	 */
	protected static class MapEntryType {
		/**
		 * the key
		 */
		@XmlElement
		@XmlIDREF
		private Student key;

		/**
		 * the double
		 */
		@XmlElement
		private Double value;

		/**
		 * Constructor for jaxb
		 */
		public MapEntryType() {
		}

		/**
		 * Convinient constructor
		 *
		 * @param student the student
		 * @param grade   the grade
		 */
		public MapEntryType(Student student, Double grade) {
			this.key = student;
			this.value = grade;
		}

		/**
		 * Returns the key or the student
		 *
		 * @return the key
		 */
		public Student getKey() {
			return key;
		}

		/**
		 * Returns the grade
		 *
		 * @return the value
		 */
		public Double getValue() {
			return value;
		}

	}
}
