package kredit.web.writeOff.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="WOF_TRN_CO")
public class TrnCo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Integer id;
	
	Integer wof_id;
	String orgCoSame;
	String orgCoFame;
	String trnCoSame;
	String trnCoFame;
	Date trnDate;
	String trnBy;

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getWof_id() {
		return wof_id;
	}
	
	public void setWof_id(Integer wof_id) {
		this.wof_id = wof_id;
	}
	
	public String getOrgCoSame() {
		return orgCoSame;
	}

	public void setOrgCoSame(String orgCoSame) {
		this.orgCoSame = orgCoSame;
	}

	public String getOrgCoFame() {
		return orgCoFame;
	}

	public void setOrgCoFame(String orgCoFame) {
		this.orgCoFame = orgCoFame;
	}

	public String getTrnCoSame() {
		return trnCoSame;
	}

	public void setTrnCoSame(String trnCoSame) {
		this.trnCoSame = trnCoSame;
	}

	public String getTrnCoFame() {
		return trnCoFame;
	}

	public void setTrnCoFame(String trnCoFame) {
		this.trnCoFame = trnCoFame;
	}

	public Date getTrnDate() {
		return trnDate;
	}
	
	public void setTrnDate(Date trnDate) {
		this.trnDate = trnDate;
	}
	
	public String getTrnBy() {
		return trnBy;
	}
	
	public void setTrnBy(String trnBy) {
		this.trnBy = trnBy;
	}
	
	public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "ID:" );
        sb.append(this.getId());
        sb.append( "|" );
        sb.append( "Write-Off Account ID:" );
        sb.append(wof_id);
        sb.append( "|" );
        sb.append( "Transfer CO" );
        
        return sb.toString();
    }

}
