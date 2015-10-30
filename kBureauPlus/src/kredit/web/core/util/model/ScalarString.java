/**
 * 
 */
package kredit.web.core.util.model;

/**
 * @author vathenan
 *
 */
public class ScalarString {
	String value;

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
}
