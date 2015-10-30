/**
 * 
 */
package kredit.web.core.util.authentication;

/**
 * @author Sovathena Neth
 *
 */
public class Priviledge {
	private boolean canCreate;
	private boolean canRead;
	private boolean canUpdate;
	private boolean canDelete;
	/**
	 * @return the canCreate
	 */
	public boolean isCanCreate() {
		return canCreate;
	}
	/**
	 * @param canCreate the canCreate to set
	 */
	public void setCanCreate(boolean canCreate) {
		this.canCreate = canCreate;
	}
	/**
	 * @return the canRead
	 */
	public boolean isCanRead() {
		return canRead;
	}
	/**
	 * @param canRead the canRead to set
	 */
	public void setCanRead(boolean canRead) {
		this.canRead = canRead;
	}
	/**
	 * @return the canUpdate
	 */
	public boolean isCanUpdate() {
		return canUpdate;
	}
	/**
	 * @param canUpdate the canUpdate to set
	 */
	public void setCanUpdate(boolean canUpdate) {
		this.canUpdate = canUpdate;
	}
	/**
	 * @return the canDelete
	 */
	public boolean isCanDelete() {
		return canDelete;
	}
	/**
	 * @param canDelete the canDelete to set
	 */
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}
	
	public Priviledge(){
		
	}
	/**
	 * @param canCreate
	 * @param canRead
	 * @param canUpdate
	 * @param canDelete
	 */
	public Priviledge(boolean canCreate, boolean canRead, boolean canUpdate,
			boolean canDelete) {
		this.canCreate = canCreate;
		this.canRead = canRead;
		this.canUpdate = canUpdate;
		this.canDelete = canDelete;
	}
	
	public Priviledge(int c, int r, int u, int d) {
		this.canCreate = c > 0;
		this.canRead = r > 0;
		this.canUpdate = u > 0;
		this.canDelete = d > 0;
	}
	
	
}
