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
        name = "competitionPostApi",
        version = "v1",
        resource = "competitionPost",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.david.example.com",
                ownerName = "backend.myapplication.david.example.com",
                packagePath = ""
        )
)
public class CompetitionPostEndpoint {

    private static final Logger logger = Logger.getLogger(CompetitionPostEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(CompetitionPost.class);
    }

    /**
     * Returns the {@link CompetitionPost} with the corresponding ID.
     *
     * @param PostID the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code CompetitionPost} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "competitionPost/{PostID}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CompetitionPost get(@Named("PostID") String PostID) throws NotFoundException {
        logger.info("Getting CompetitionPost with ID: " + PostID);
        CompetitionPost competitionPost = ofy().load().type(CompetitionPost.class).id(PostID).now();
        if (competitionPost == null) {
            throw new NotFoundException("Could not find CompetitionPost with ID: " + PostID);
        }
        return competitionPost;
    }

    /**
     * Inserts a new {@code CompetitionPost}.
     */
    @ApiMethod(
            name = "insert",
            path = "competitionPost",
            httpMethod = ApiMethod.HttpMethod.POST)
    public CompetitionPost insert(CompetitionPost competitionPost) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that competitionPost.PostID has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(competitionPost).now();
        logger.info("Created CompetitionPost.");

        return ofy().load().entity(competitionPost).now();
    }

    /**
     * Updates an existing {@code CompetitionPost}.
     *
     * @param PostID          the ID of the entity to be updated
     * @param competitionPost the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code PostID} does not correspond to an existing
     *                           {@code CompetitionPost}
     */
    @ApiMethod(
            name = "update",
            path = "competitionPost/{PostID}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public CompetitionPost update(@Named("PostID") String PostID, CompetitionPost competitionPost) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(PostID);
        ofy().save().entity(competitionPost).now();
        logger.info("Updated CompetitionPost: " + competitionPost);
        return ofy().load().entity(competitionPost).now();
    }

    /**
     * Deletes the specified {@code CompetitionPost}.
     *
     * @param PostID the ID of the entity to delete
     * @throws NotFoundException if the {@code PostID} does not correspond to an existing
     *                           {@code CompetitionPost}
     */
    @ApiMethod(
            name = "remove",
            path = "competitionPost/{PostID}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("PostID") String PostID) throws NotFoundException {
        checkExists(PostID);
        ofy().delete().type(CompetitionPost.class).id(PostID).now();
        logger.info("Deleted CompetitionPost with ID: " + PostID);
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
            path = "competitionPost",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<CompetitionPost> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<CompetitionPost> query = ofy().load().type(CompetitionPost.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<CompetitionPost> queryIterator = query.iterator();
        List<CompetitionPost> competitionPostList = new ArrayList<CompetitionPost>(limit);
        while (queryIterator.hasNext()) {
            competitionPostList.add(queryIterator.next());
        }
        return CollectionResponse.<CompetitionPost>builder().setItems(competitionPostList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String PostID) throws NotFoundException {
        try {
            ofy().load().type(CompetitionPost.class).id(PostID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find CompetitionPost with ID: " + PostID);
        }
    }
}