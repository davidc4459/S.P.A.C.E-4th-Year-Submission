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
        name = "youthClubApi",
        version = "v1",
        resource = "youthClub",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.david.example.com",
                ownerName = "backend.myapplication.david.example.com",
                packagePath = ""
        )
)
public class YouthClubEndpoint {

    private static final Logger logger = Logger.getLogger(YouthClubEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(YouthClub.class);
    }

    /**
     * Returns the {@link YouthClub} with the corresponding ID.
     *
     * @param YouthClub the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code YouthClub} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "youthClub/{YouthClub}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public YouthClub get(@Named("YouthClub") String YouthClub) throws NotFoundException {
        logger.info("Getting YouthClub with ID: " + YouthClub);
        YouthClub youthClub = ofy().load().type(YouthClub.class).id(YouthClub).now();
        if (youthClub == null) {
            throw new NotFoundException("Could not find YouthClub with ID: " + YouthClub);
        }
        return youthClub;
    }

    /**
     * Inserts a new {@code YouthClub}.
     */
    @ApiMethod(
            name = "insert",
            path = "youthClub",
            httpMethod = ApiMethod.HttpMethod.POST)
    public YouthClub insert(YouthClub youthClub) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that youthClub.YouthClub has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(youthClub).now();
        logger.info("Created YouthClub with ID: " + youthClub.getYouthClub());

        return ofy().load().entity(youthClub).now();
    }

    /**
     * Updates an existing {@code YouthClub}.
     *
     * @param YouthClub the ID of the entity to be updated
     * @param youthClub the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code YouthClub} does not correspond to an existing
     *                           {@code YouthClub}
     */
    @ApiMethod(
            name = "update",
            path = "youthClub/{YouthClub}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public YouthClub update(@Named("YouthClub") String YouthClub, YouthClub youthClub) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(YouthClub);
        ofy().save().entity(youthClub).now();
        logger.info("Updated YouthClub: " + youthClub);
        return ofy().load().entity(youthClub).now();
    }

    /**
     * Deletes the specified {@code YouthClub}.
     *
     * @param YouthClub the ID of the entity to delete
     * @throws NotFoundException if the {@code YouthClub} does not correspond to an existing
     *                           {@code YouthClub}
     */
    @ApiMethod(
            name = "remove",
            path = "youthClub/{YouthClub}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("YouthClub") String YouthClub) throws NotFoundException {
        checkExists(YouthClub);
        ofy().delete().type(YouthClub.class).id(YouthClub).now();
        logger.info("Deleted YouthClub with ID: " + YouthClub);
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
            path = "youthClub",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<YouthClub> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<YouthClub> query = ofy().load().type(YouthClub.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<YouthClub> queryIterator = query.iterator();
        List<YouthClub> youthClubList = new ArrayList<YouthClub>(limit);
        while (queryIterator.hasNext()) {
            youthClubList.add(queryIterator.next());
        }
        return CollectionResponse.<YouthClub>builder().setItems(youthClubList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String YouthClub) throws NotFoundException {
        try {
            ofy().load().type(YouthClub.class).id(YouthClub).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find YouthClub with ID: " + YouthClub);
        }
    }
}