package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class CreateAppointmentHandler implements RequestHandler<ApiGatewayRequest,ApiGatewayResponse> {
    private appointmentdao appointmentdao = new appointmentdao();
    @Override
    public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> origin = new HashMap<>();
        origin.put("Access-Control-Allow-Origin", "*");
        try {
            appointmentmodel appointment = mapper.readValue((String) input.getBody(), appointmentmodel.class);
            appointmentdao.insert(appointment);
            return ApiGatewayResponse.builder().setHeaders(origin).setStatusCode(200).setObjectBody(appointment).build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ApiGatewayResponse.builder().setHeaders(origin).setStatusCode(500).setObjectBody(input).build();
    }
}
