package com.study.realworld.domain.tag.application;

import com.study.realworld.domain.tag.domain.persist.Tag;
import com.study.realworld.domain.tag.domain.persist.TagRepository;
import com.study.realworld.domain.tag.domain.vo.TagName;
import com.study.realworld.domain.tag.dto.TagSave;
import com.study.realworld.domain.tag.error.DuplicatedTagNameException;
import com.study.realworld.domain.user.error.exception.DuplicatedEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;

@DisplayName("태그 커멘드 서비스(TagCommandService)")
@ExtendWith(MockitoExtension.class)
class TagCommandServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagCommandService tagCommandService;

    @Test
    void 올바른_데이터_입력시_저장한다() {
        final TagName tagName = TagName.from("tagName");
        final Tag expected = new Tag(tagName);
        final TagSave.Request request = TagSave.Request.from(tagName);

        willReturn(false).given(tagRepository).existsByTagName(any());
        willReturn(expected).given(tagRepository).save(any());

        final Tag actual = tagCommandService.save(request);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 올바르지_않은_데이터_입력시_저장되지_않는다() {
        final TagName tagName = TagName.from("tagName");
        final TagSave.Request request = TagSave.Request.from(tagName);

        willReturn(true).given(tagRepository).existsByTagName(any());

        assertThatThrownBy(() -> tagCommandService.save(request))
                .isExactlyInstanceOf(DuplicatedTagNameException.class)
                .hasMessage(String.format("태그 이름 : [ %s ] 가 이미 존재합니다.", tagName.tagName()));
    }
}
