/*
 Storybook: Open Source software for novelists and authors.
 Copyright (C) 2008 - 2012 Martin Mustun

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package storybook.model.hbn.entity;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.Icon;

import org.apache.commons.lang3.time.DateUtils;
import storybook.model.state.SceneState;
import storybook.model.state.SceneStateModel;
import storybook.toolkit.DateUtil;
import storybook.toolkit.I18N;
import storybook.toolkit.TextUtil;
import storybook.toolkit.html.HtmlUtil;

/**
 * Scene generated by hbm2java
 *
 * @hibernate.class table="SCENE"
 */
public class Scene extends AbstractEntity implements Comparable<Scene> {

    private Chapter chapter;
    private Strand strand;
    private Integer sceneno;
    private Timestamp sceneTs;
    private String title;
    private String summary;
    private Integer status;
    private Integer relativeDateDifference;
    private Long relativeSceneId;
    private String notes;
    /* new notes
     debut d'en-tete par $£€ø
     première partie calendrier spécifique sous la forme YYYYYY/MMM/JJJ HHH:MMM:SSS
     année 6 chiffre
     mois 3 chiffres
     jour 3 chiffres
     heure 3 chiffres
     minutes 3 chiffres
     seconde 3 chiffres
     séparateur **
     deuxième partie nom du fichier ODF y compris chemin et extension
     fin d'en-tete ø€£$
     */
    private Boolean informative;

    private List<Person> persons;
    private List<Location> locations;
    private List<Strand> strands;

    public Scene() {
        super();
    }

    public Scene(Chapter chapter, Strand strand, Integer sceneno, Timestamp date,
            String title, String summary, Integer status,
            Integer relativeDateDifference, Long relativeSceneId,
            String notes, Boolean informative) {
        this.chapter = chapter;
        this.strand = strand;
        this.sceneno = sceneno;
        this.sceneTs = date;
        this.title = title;
        this.summary = summary;
        this.status = status;
        this.relativeDateDifference = relativeDateDifference;
        this.relativeSceneId = relativeSceneId;
        this.notes = notes;
        this.informative = informative;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chapter getChapter() {
        return this.chapter;
    }

    public boolean hasChapter() {
        return chapter != null;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public void setChapter() {
        this.chapter = null;
    }

    public Strand getStrand() {
        return this.strand;
    }

    public void setStrand(Strand strand) {
        this.strand = strand;
    }

    public Integer getSceneno() {
        return this.sceneno;
    }

    public boolean hasSceneno() {
        return sceneno != null;
    }

    public void setSceneno(Integer sceneno) {
        this.sceneno = sceneno;
    }

    public Timestamp getSceneTs() {
        return this.sceneTs;
    }

    public String getSceneTime() {
        //TODO getSceneTime for new calendar
        String r = "";
        return (r);
    }

    public boolean hasSceneTs() {
        return sceneTs != null;
    }

    public Date getDate() {
        if (sceneTs == null) {
            return null;
        }
		// OLD: return new Date(sceneTs.getTime());
        // remove time from date
        return DateUtil.getZeroTimeDate(new Date(sceneTs.getTime()));
    }

    public void removeSceneTs() {
        sceneTs = null;
    }

    public String getDateStrShort() {
        if (!hasSceneTs()) {
            return "";
        }
        DateFormat formatter;
        if (DateUtil.isZeroTimeDate(sceneTs)) {
            formatter = I18N.getShortDateFormatter();
        } else {
            formatter = I18N.getDateTimeFormatter();
        }
        return formatter.format(sceneTs);
    }

    public String getDateStrMedium() {
        if (!hasSceneTs()) {
            return "";
        }
        DateFormat formatter;
        if (DateUtil.isZeroTimeDate(sceneTs)) {
            formatter = I18N.getMediumDateFormatter();
        } else {
            formatter = I18N.getDateTimeFormatter();
        }
        return formatter.format(sceneTs);
    }

    public String getDateStrLong() {
        if (!hasSceneTs()) {
            return "";
        }
        DateFormat formatter;
        if (DateUtil.isZeroTimeDate(sceneTs)) {
            formatter = I18N.getLongDateFormatter();
        } else {
            formatter = I18N.getDateTimeFormatter();
        }
        return formatter.format(sceneTs);
    }

    public void setDate(Date date) {
        if (date == null) {
            sceneTs = null;
            return;
        }
        sceneTs = new Timestamp(date.getTime());
    }

    public void setSceneTs(Timestamp ts) {
        sceneTs = ts;
    }

    public void setSceneTime(String t) {
        //TODO setSceneTime
    }

    public void setNoSceneTs() {
        // nothing to do
    }

    public void setNoSceneTs(String val) {
        // nothing to do
    }

    public void getNoSceneTs() {
        // nothing to do
    }

    public void removeNoSceneTs() {
        // nothing to do
    }

    public boolean hasNoSceneTs() {
        return sceneTs == null;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle(boolean truncate) {
        if (truncate) {
            return TextUtil.ellipsize(title, 30);
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullTitle() {
        return getFullTitle(false);
    }

    public String getFullTitle(boolean truncate) {
        return getFullTitle(false, truncate);
    }

    public String getFullTitle(boolean timestamp, boolean truncate) {
        StringBuilder buf = new StringBuilder();
        buf.append(getChapterSceneNo());
        buf.append(getTitle(truncate));
        if (timestamp && hasSceneTs()) {
            buf.append(" (");
            buf.append(getDateStrMedium());
            buf.append(")");
        }
        return buf.toString();
    }

    public String getTitleText(boolean truncate, int length) {
        if (title == null || title.isEmpty()) {
            return getText(truncate, length);
        }
        return getTitle(truncate) + ": " + getText(truncate, length);
    }

    public String getChapterSceneNo() {
        return getChapterSceneNo(true);
    }

    public String getChapterSceneNo(boolean appendColon) {
        StringBuilder buf = new StringBuilder();
        if (hasChapter()) {
            buf.append(chapter.getChapterno());
        } else {
            buf.append("x");
        }
        buf.append(".");
        if (hasSceneno()) {
            buf.append(sceneno);
        } else {
            buf.append("x");
        }
        if (appendColon) {
            buf.append(": ");
        }
        return buf.toString();
    }

    public String getChapterSceneToolTip() {
        if (!hasChapter()) {
            return "";
        }
        StringBuilder buf = new StringBuilder("<html>");
        buf.append(I18N.getMsgColon("msg.common.chapter"));
        buf.append(" ").append(getChapter().toString());
        buf.append("<br>");
        if (getChapter().hasPart()) {
            buf.append(I18N.getMsgColon("msg.common.part"));
            buf.append(" ").append(getChapter().getPart().toString());
            buf.append("<br>");
        }
        return buf.toString();
    }

    public String getSummary() {
        return this.summary;
    }

    public String getSummary(boolean truncate, int length) {
        if (truncate) {
            return TextUtil.truncateString(HtmlUtil.htmlToText(summary), length);
        }
        return this.summary;
    }

    public String getText() {
        return getSummary();
    }

    public String getText(boolean truncate, int length) {
        return getSummary(truncate, length);
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Icon getStatusIcon() {
        return getSceneState().getIcon();
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setSceneState(SceneState state) {
        this.status = state.getNumber();
    }

    public SceneState getSceneState() {
        SceneStateModel model = new SceneStateModel();
        return (SceneState) model.findByNumber(this.status);
    }

    public Integer getRelativeDateDifference() {
        return this.relativeDateDifference;
    }

    public void setRelativeDateDifference(Integer relativeDateDifference) {
        this.relativeDateDifference = relativeDateDifference;
    }

    public Date getRelativeDate(Scene relativeScene) {
        if (relativeScene == null) {
            return null;
        }
        Date date = relativeScene.getDate();
        if (date == null) {
            return null;
        }
        return DateUtils.addDays(date, relativeDateDifference);
    }

    public Long getRelativeSceneId() {
        return this.relativeSceneId;
    }

    public void setRelativeSceneId(Long relativeSceneId) {
        this.relativeSceneId = relativeSceneId;
    }

    public void setRelativeSceneId(Scene relativeScene) {
        if (relativeScene == null) {
            return;
        }
        setRelativeSceneId(relativeScene.id);
    }

    public boolean hasRelativeScene() {
        return this.relativeSceneId != null;
    }

    public void removeRelativeScene() {
        relativeSceneId = null;
        relativeDateDifference = null;
    }

    public String getNotes() {
        return getNotes(false);
    }

    public String getNotes(boolean truncate) {
        if (this.notes == null) {
            return "";
        }
		String x=this.notes;
		if (truncate) {
			x=TextUtil.truncateString(HtmlUtil.htmlToText(x), 200);
		}
		return(x);
	}

    public void setNotes(String notes) {
		this.notes=notes;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
//		System.out.println("Scene.setPersons(): persons:"+persons);
        this.persons = persons;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Strand> getStrands() {
        return strands;
    }

    public void setStrands(List<Strand> strands) {
        this.strands = strands;
    }

    public Boolean getInformative() {
        if (informative == null) {
            return false;
        }
        return informative;
    }

    public void setInformative(Boolean informative) {
        this.informative = informative;
    }

    @Override
    public Icon getIcon() {
        return I18N.getIcon("icon.small.scene");
    }

    @Override
    public String toString() {
        if (isTransient()) {
//			return I18N.getMsg("msg.common.scene") + " [" + getTransientId() + "]";
            return "";
        }
        return getFullTitle(false, true);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Scene test = (Scene) obj;

        if (id != null ? !id.equals(test.id) : test.id != null) {
            return false;
        }
        boolean ret = true;
        ret = ret && equalsIntegerNullValue(sceneno, test.getSceneno());
        ret = ret && equalsIntegerNullValue(status, test.getStatus());
        ret = ret && equalsObjectNullValue(chapter, test.getChapter());
        ret = ret && equalsObjectNullValue(strand, test.getStrand());
        if (sceneTs != null) {
            ret = ret && equalsTimestampNullValue(sceneTs, test.getSceneTs());
        } else if (relativeSceneId != null) {
            ret = ret && equalsLongNullValue(relativeSceneId, test.getRelativeSceneId());
            ret = ret && equalsIntegerNullValue(relativeDateDifference, test.getRelativeDateDifference());
        }
        ret = ret && equalsStringNullValue(title, test.getTitle());
        ret = ret && equalsStringNullValue(summary, test.getSummary());
        ret = ret && equalsStringNullValue(notes, test.getNotes());
        ret = ret && equalsBooleanNullValue(informative, test.getInformative());
        ret = ret && equalsListNullValue(persons, test.getPersons());
        ret = ret && equalsListNullValue(locations, test.getLocations());
        ret = ret && equalsListNullValue(strands, test.getStrands());
        return ret;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = hash * 31 + (sceneno != null ? sceneno.hashCode() : 0);
        hash = hash * 31 + (status != null ? status.hashCode() : 0);
        hash = hash * 31 + (chapter != null ? chapter.hashCode() : 0);
        hash = hash * 31 + (strand != null ? strand.hashCode() : 0);
        hash = hash * 31 + (sceneTs != null ? sceneTs.hashCode() : 0);
        hash = hash * 31 + (relativeSceneId != null ? relativeSceneId.hashCode() : 0);
        hash = hash * 31 + (relativeDateDifference != null ? relativeDateDifference.hashCode() : 0);
        hash = hash * 31 + (title != null ? title.hashCode() : 0);
        hash = hash * 31 + (summary != null ? summary.hashCode() : 0);
        hash = hash * 31 + (notes != null ? notes.hashCode() : 0);
        hash = hash * 31 + (informative != null ? informative.hashCode() : 0);
        hash = hash * 31 + (persons != null ? getListHashCode(persons) : 0);
        hash = hash * 31 + (locations != null ? getListHashCode(locations) : 0);
        hash = hash * 31 + (strands != null ? getListHashCode(strands) : 0);
        return hash;
    }

    @Override
    public int compareTo(Scene o) {
        int cmp = chapter.compareTo(o.chapter);
        if (cmp == 0) {
            return sceneno.compareTo(o.sceneno);
        }
        return cmp;
    }

    public String getODF() {
        String r = "";
        if (notes.startsWith("$£€ø")) {
            String z[] = notes.split("ø€£$");
            String y[] = z[0].split("**");
            r = y[1];
        }
        return (r);
    }

    public String getCalendar() {
        String r = "";
        if (notes.startsWith("$£€ø")) {
            String z[] = notes.split("ø€£$");
            String y[] = z[0].split("**");
            r = y[0].substring("$£€ø".length());
        }
        return (r);
    }

    public void setODF(String nf) {
        /*String r = "";
        if (notes.startsWith("$£€ø")) {
            String cal = getCalendar();
            String nt = getNotesNew();
            notes = "$£€ø" + cal + "**" + nf + "ø€£$" + nt;
        } else {
            String cal = "";
            notes = "$£€ø" + cal + "**" + nf + "ø€£$" + notes;
		}*/
    }

    public void setCalendar(String cal) {
        /*String r = "";
        if (notes.startsWith("$£€ø")) {
            String nf = getODF();
            String nt = getNotesNew();
            notes = "$£€ø" + cal + "**" + nf + "ø€£$" + nt;
        } else {
            String nf = "";
            notes = "$£€ø" + cal + "**" + nf + "ø€£$" + notes;
		}*/
    }

    public static String tsToTime(Timestamp ts) {
        String r = "";
        //TODO tsToTime for new calendar
        return (r);
    }

 /*   public String sceneNotesConversion(Scene scene) {
        String n = scene.getNotes();
        if (!n.startsWith("$£€ø")) {
            n = "$£€ø" + "**" + "ø€£$" + getNotes();
        }
        return (n);
    }*/

    public int numberOfCharacters() {
        int nb = HtmlUtil.htmlToText(summary).length();
        return (nb);
    }

    public int numberOfWords() {
        int nb = TextUtil.countWords(summary);
        return (nb);
    }
}
