package com.website.shared.metadata;

import com.website.shared.security.AuthorityEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Info about who's calling a service method - filled in automatically by
 * MetadataHandlerAspect before the method body runs, whenever the method is
 * annotated with @MetadataHandler and takes a Metadata as its first parameter.
 * Callers just pass `new Metadata()` - they don't need to fill in any fields themselves.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Metadata {
  private String userId;
  private String email;
  private AuthorityEnum authority;
}
