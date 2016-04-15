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
        name = "adminLoginApi",
        version = "v1",
        resource = "adminLogin",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.david.example.com",
                ownerName = "backend.myapplication.david.example.com",
                packagePath = ""
        )
)
public class AdminLoginEndpoint {

    private static final Logger logger = Logger.getLogger(AdminLoginEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(AdminLogin.class);
    }

    /**
     * Returns the {@link AdminLogin} with the corresponding ID.
     *
     * @param AdminID the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code AdminLogin} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "adminLogin/{AdminID}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public AdminLogin get(@Named("AdminID") String AdminID) throws NotFoundException {
        logger.info("Getting AdminLogin with ID: " + AdminID);
        AdminLogin adminLogin = ofy().load().type(AdminLogin.class).id(AdminID).now();
        if (adminLogin == null) {
            throw new NotFoundException("Could not find AdminLogin with ID: " + AdminID);
        }
        return adminLogin;
    }

    /**
     * Inserts a new {@code AdminLogin}.
     */
    @ApiMethod(
            name = "insert",
            path = "adminLogin",
            httpMethod = ApiMethod.HttpMethod.POST)
    public AdminLogin insert(AdminLogin adminLogin) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that adminLogin.AdminID has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(adminLogin).now();
        logger.info("Created AdminLogin with ID: " + adminLogin.getAdminID());

        return ofy().load().entity(adminLogin).now();
    }

    /**
     * Updates an existing {@code AdminLogin}.
     *
     * @param AdminID    the ID of the entity to be updated
     * @param adminLogin the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code AdminID} does not correspond to an existing
     *                           {@code AdminLogin}
     */
    @ApiMethod(
            name = "update",
            path = "adminLogin/{AdminID}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public AdminLogin update(@Named("AdminID") String AdminID, AdminLogin adminLogin) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(AdminID);
        ofy().save().entity(adminLogin).now();
        logger.info("Updated AdminLogin: " + adminLogin);
        return ofy().load().entity(adminLogin).now();
    }

    /**
     * Deletes the specified {@code AdminLogin}.
     *
     * @param AdminID the ID of the entity to delete
     * @throws NotFoundException if the {@code AdminID} does not correspond to an existing
     *                           {@code AdminLogin}
     */
    @ApiMethod(
            name = "remove",
            path = "adminLogin/{AdminID}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("AdminID") String AdminID) throws NotFoundException {
        checkExists(AdminID);
        ofy().delete().type(AdminLogin.class).id(AdminID).now();
        logger.info("Deleted AdminLogin with ID: " + AdminID);
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
            path = "adminLogin",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<AdminLogin> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<AdminLogin> query = ofy().load().type(AdminLogin.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<AdminLogin> queryIterator = query.iterator();
        List<AdminLogin> adminLoginList = new ArrayList<AdminLogin>(limit);
        while (queryIterator.hasNext()) {
            adminLoginList.add(queryIterator.next());
        }
        return CollectionResponse.<AdminLogin>builder().setItems(adminLoginList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String AdminID) throws NotFoundException {
        try {
            ofy().load().type(AdminLogin.class).id(AdminID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find AdminLogin with ID: " + AdminID);
        }
    }
}