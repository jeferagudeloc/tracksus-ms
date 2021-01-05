package com.ven.cwt.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ven.cwt.app.entity.Request;

@RepositoryRestResource
public interface RequestRepository extends MongoRepository<Request, String>  {
	  public Request findByUrl(String url);
}
