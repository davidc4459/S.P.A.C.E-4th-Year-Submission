package com.example.david.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "userLoginApi",
        version = "v1",
        resource = "userLogin",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.david.example.com",
                ownerName = "backend.myapplication.david.example.com",
                packagePath = ""
        )
)
public class UserLoginEndpoint {

    private static final Logger logger = Logger.getLogger(UserLoginEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(UserLogin.class);
    }

    /**
     * Returns the {@link UserLogin} with the corresponding ID.
     *
     * @param UserID the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code UserLogin} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "userLogin/{UserID}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public UserLogin get(@Named("UserID") String UserID) throws NotFoundException {
        logger.info("Getting UserLogin with ID: " + UserID);
        UserLogin userLogin = ofy().load().type(UserLogin.class).id(UserID).now();
        if (userLogin == null) {
            throw new NotFoundException("Could not find UserLogin with ID: " + UserID);
        }
        return userLogin;
    }

    /**
     * Inserts a new {@code UserLogin}.
     */
    @ApiMethod(
            name = "insert",
            path = "userLogin",
            httpMethod = ApiMethod.HttpMethod.POST)
    public UserLogin insert(UserLogin userLogin) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that userLogin.UserID has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(userLogin).now();
        logger.info("Created UserLogin with ID: " + userLogin.getUserID());

        return ofy().load().entity(userLogin).now();
    }

    /**
     * Updates an existing {@code UserLogin}.
     *
     * @param UserID    the ID of the entity to be updated
     * @param userLogin the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code UserID} does not correspond to an existing
     *                           {@code UserLogin}
     */
    @ApiMethod(
            name = "update",
            path = "userLogin/{UserID}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public UserLogin update(@Named("UserID") String UserID, UserLogin userLogin) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(UserID);
        ofy().save().entity(userLogin).now();
        logger.info("Updated UserLogin: " + userLogin);
        return ofy().load().entity(userLogin).now();
    }

    /**
     * Deletes the specified {@code UserLogin}.
     *
     * @param UserID the ID of the entity to delete
     * @throws NotFoundException if the {@code UserID} does not correspond to an existing
     *                           {@code UserLogin}
     */
    @ApiMethod(
            name = "remove",
            path = "userLogin/{UserID}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("UserID") String UserID) throws NotFoundException {
        checkExists(UserID);
        ofy().delete().type(UserLogin.class).id(UserID).now();
        logger.info("Deleted UserLogin with ID: " + UserID);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "userLogin",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<UserLogin> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<UserLogin> query = ofy().load().type(UserLogin.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<UserLogin> queryIterator = query.iterator();
        List<UserLogin> userLoginList = new ArrayList<UserLogin>(limit);
        while (queryIterator.hasNext()) {
            userLoginList.add(queryIterator.next());
        }
        return CollectionResponse.<UserLogin>builder().setItems(userLoginList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String UserID) throws NotFoundException {
        try {
            ofy().load().type(UserLogin.class).id(UserID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find UserLogin with ID: " + UserID);
        }
    }
}