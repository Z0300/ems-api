package com.api.ems.entities.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum EventType {
   ON_SITE("On Site"),
   VIRTUAL("Virtual");

   private final String label;

   EventType(String label) {
      this.label = label;
   }

   @JsonValue
   public String getLabel() {
      return label;
   }
}
