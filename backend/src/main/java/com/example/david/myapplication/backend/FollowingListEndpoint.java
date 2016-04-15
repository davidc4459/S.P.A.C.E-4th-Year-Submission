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
        name = "followingListApi",
        version = "v1",
        resource = "followingList",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.david.example.com",
                ownerName = "backend.myapplication.david.example.com",
                packagePath = ""
        )
)
public class FollowingListEndpoint {

    private static final Logger logger = Logger.getLogger(FollowingListEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(FollowingList.class);
    }

    /**
     * Returns the {@link FollowingList} with the corresponding ID.
     *
     * @param FollowID the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code FollowingList} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "followingList/{FollowID}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public FollowingList get(@Named("FollowID") String FollowID) throws NotFoundException {
        logger.info("Getting FollowingList with ID: " + FollowID);
        FollowingList followingList = ofy().load().type(FollowingList.class).id(FollowID).now();
        if (followingList == null) {
            throw new NotFoundException("Could not find FollowingList with ID: " + FollowID);
        }
        return followingList;
    }

    /**
     * Inserts a new {@code FollowingList}.
     */
    @ApiMethod(
            name = "insert",
            path = "followingList",
            httpMethod = ApiMethod.HttpMethod.POST)
    public FollowingList insert(FollowingList followingList) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that followingList.FollowID has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(followingList).now();
        logger.info("Created FollowingList with ID: " + followingList.getFollowID());

        return ofy().load().entity(followingList).now();
    }

    /**
     * Updates an existing {@code FollowingList}.
     *
     * @param FollowID      the ID of the entity to be updated
     * @param followingList the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code FollowID} does not correspond to an existing
     *                           {@code FollowingList}
     */
    @ApiMethod(
            name = "update",
            path = "followingList/{FollowID}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public FollowingList update(@Named("FollowID") String FollowID, FollowingList followingList) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(FollowID);
        ofy().save().entity(followingList).now();
        logger.info("Updated FollowingList: " + followingList);
        return ofy().load().entity(followingList).now();
    }

    /**
     * Deletes the specified {@code FollowingList}.
     *
     * @param FollowID the ID of the entity to delete
     * @throws NotFoundException if the {@code FollowID} does not correspond to an existing
     *                           {@code FollowingList}
     */
    @ApiMethod(
            name = "remove",
            path = "followingList/{FollowID}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("FollowID") String FollowID) throws NotFoundException {
        checkExists(FollowID);
        ofy().delete().type(FollowingList.class).id(FollowID).now();
        logger.info("Deleted FollowingList with ID: " + FollowID);
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
            path = "followingList",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<FollowingList> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<FollowingList> query = ofy().load().type(FollowingList.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<FollowingList> queryIterator = query.iterator();
        List<FollowingList> followingListList = new ArrayList<FollowingList>(limit);
        while (queryIterator.hasNext()) {
            followingListList.add(queryIterator.next());
        }
        return CollectionResponse.<FollowingList>builder().setItems(followingListList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String FollowID) throws NotFoundException {
        try {
            ofy().load().type(FollowingList.class).id(FollowID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find FollowingList with ID: " + FollowID);
        }
    }
}