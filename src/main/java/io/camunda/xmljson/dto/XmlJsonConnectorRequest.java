package io.camunda.xmljson.dto;

import java.util.Objects;

public class XmlJsonConnectorRequest {

    private String messageInXml;
    private String messageInJson;
    private String choice;

    public String getMessageInXml() {
        return messageInXml;
    }

    public void setMessageInXml(String messageInXml) {
        this.messageInXml = messageInXml;
    }

    public String getMessageInJson() {
        return messageInJson;
    }

    public void setMessageInJson(String messageInJson) {
        this.messageInJson = messageInJson;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        XmlJsonConnectorRequest other = (XmlJsonConnectorRequest) obj;
        return Objects.equals(messageInJson, other.messageInJson) &&
            Objects.equals(choice, other.choice) &&
            Objects.equals(messageInXml, other.messageInXml);
    }

    @Override
    public String toString() {
        return "XmlJsonConnectorRequest [choice=" + choice + ", messageInXml=" + messageInXml + ", messageInJson=" + messageInJson + "]";
    }
}