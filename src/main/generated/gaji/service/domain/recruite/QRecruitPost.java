package gaji.service.domain.recruite;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitPost is a Querydsl query type for RecruitPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitPost extends EntityPathBase<RecruitPost> {

    private static final long serialVersionUID = 129501619L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitPost recruitPost = new QRecruitPost("recruitPost");

    public final gaji.service.domain.common.entity.QBaseEntity _super = new gaji.service.domain.common.entity.QBaseEntity(this);

    public final NumberPath<Integer> bookmarks = createNumber("bookmarks", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> EndTime = createDate("EndTime", java.time.LocalDate.class);

    public final NumberPath<Integer> headCount = createNumber("headCount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath inviteCode = createString("inviteCode");

    public final BooleanPath isPrivate = createBoolean("isPrivate");

    public final BooleanPath isRecruited = createBoolean("isRecruited");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> LastModifiedDate = _super.LastModifiedDate;

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final BooleanPath peopleLimited = createBoolean("peopleLimited");

    public final NumberPath<Integer> peopleMaximum = createNumber("peopleMaximum", Integer.class);

    public final EnumPath<gaji.service.domain.enums.RecruitePostTypeEnum> recruitePostTypeEnum = createEnum("recruitePostTypeEnum", gaji.service.domain.enums.RecruitePostTypeEnum.class);

    public final ListPath<RecruitPostBookmark, QRecruitPostBookmark> recruitPostBookmarkList = this.<RecruitPostBookmark, QRecruitPostBookmark>createList("recruitPostBookmarkList", RecruitPostBookmark.class, QRecruitPostBookmark.class, PathInits.DIRECT2);

    public final ListPath<RecruitPostLikes, QRecruitPostLikes> recruitPostLikesList = this.<RecruitPostLikes, QRecruitPostLikes>createList("recruitPostLikesList", RecruitPostLikes.class, QRecruitPostLikes.class, PathInits.DIRECT2);

    public final gaji.service.domain.room.entity.QRoom room;

    public final ListPath<SelectCategory, QSelectCategory> selectCategoryList = this.<SelectCategory, QSelectCategory>createList("selectCategoryList", SelectCategory.class, QSelectCategory.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> StartTime = createDate("StartTime", java.time.LocalDate.class);

    public final StringPath thumbnailPath = createString("thumbnailPath");

    public final StringPath title = createString("title");

    public final gaji.service.domain.user.entity.QUser user;

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public QRecruitPost(String variable) {
        this(RecruitPost.class, forVariable(variable), INITS);
    }

    public QRecruitPost(Path<? extends RecruitPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitPost(PathMetadata metadata, PathInits inits) {
        this(RecruitPost.class, metadata, inits);
    }

    public QRecruitPost(Class<? extends RecruitPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new gaji.service.domain.room.entity.QRoom(forProperty("room"), inits.get("room")) : null;
        this.user = inits.isInitialized("user") ? new gaji.service.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

