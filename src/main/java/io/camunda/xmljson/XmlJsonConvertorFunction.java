package io.camunda.xmljson;

import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.xmljson.dto.XmlJsonConnectorRequest;
import io.camunda.xmljson.dto.XmlJsonConnectorResult;
import org.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OutboundConnector(
    name = "XML JSON",
    inputVariables = {
        "choice",
        "messageInXml",
        "messageInJson"
    },
    type = "io.camunda:xml-json:1")

public class XmlJsonConvertorFunction implements OutboundConnectorFunction {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XmlJsonConvertorFunction.class);

    @Override
    public Object execute(OutboundConnectorContext context) {
        final
        var connectorRequest = context.bindVariables(XmlJsonConnectorRequest.class);
        return executeConnector(connectorRequest);
    }

    private XmlJsonConnectorResult executeConnector(final XmlJsonConnectorRequest connectorRequest) {
        String choice = connectorRequest.getChoice();
        String result = null;

        if ("jsonToXml".equals(choice)) { //if selected option is from JSON to XML
            String messageInJson = connectorRequest.getMessageInJson();
            String cleanedJson = preprocessJsonString(messageInJson); // processing the format of json
            result = jsonToXmlFunction(cleanedJson);
        } else {
            String messageInXml = connectorRequest.getMessageInXml();
            result = xmlToJsonFunction(messageInXml);
        }

        return new XmlJsonConnectorResult(result);
    }
    
    // Function to clean the JSON data received from Connector
    private String preprocessJsonString(String inputJson) {
        // Replace escape characters and newlines
        String cleanedJson = inputJson.replace("\\n", "").replace("\\\"", "\"");
        return cleanedJson;
    }
    
    // Function to convert XML data to JSON format
    private String xmlToJsonFunction(String messageInXml) {
        String jsonString = null;
        try {
            JSONObject json = XML.toJSONObject(messageInXml);
            String jsonUncleaned = json.toString(4); // for Indentation purpose
            jsonString = preprocessJsonString(jsonUncleaned);
            LOGGER.info("Formatted JSON String: " + jsonString);
            
        } catch (JSONException e) {
            jsonString = "{\"error\": \"Failed to convert XML to JSON\"}";
            System.out.println(e.toString());
        }

        return jsonString;
    }
    
    // Function to convert JSON data to XML format
    private String jsonToXmlFunction(String messageInJson) {
        String xmlString = null;

        try {
            JSONObject json = new JSONObject(messageInJson);
            xmlString = XML.toString(json);
            LOGGER.info("Formatted XML String: " + xmlString);
        } catch (JSONException e) {
            xmlString = "<error>Failed to convert JSON to XML</error>";
            System.out.println(e.toString());
        }

        return xmlString;
    }
}