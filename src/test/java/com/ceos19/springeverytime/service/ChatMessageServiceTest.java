package com.ceos19.springeverytime.service;

import com.ceos19.springeverytime.domain.ChatMessage;
import com.ceos19.springeverytime.domain.ChatRoom;
import com.ceos19.springeverytime.domain.User;
import com.ceos19.springeverytime.repository.ChatMessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ChatMessageServiceTest {
    @Mock
    ChatMessageRepository chatMessageRepository;

    @InjectMocks
    ChatMessageService chatMessageService;

    User user1, user2;
    ChatRoom chatRoom;

    @BeforeEach
    void 테스트_셋업() {
        user1 = new User(
                "test",
                "adsfbsa234@ad",
                "nicnname",
                "kim",
                "computer",
                "20",
                "test@exmaple.com"
        );

        user2 = new User(
                "test2",
                "adsfbsa234@ad",
                "nickname2",
                "kwon",
                "data",
                "21",
                "test2@exmaple.com"
        );

        chatRoom = new ChatRoom(user1, user2);
    }

    @Test
    @DisplayName("채팅 내역 조회 테스트")
    void 채팅_내역_조회_테스트() {
        // given
        ChatMessage chatMessage1 = new ChatMessage("안녕하세요", new Date(), chatRoom, user1);
        ChatMessage chatMessage2= new ChatMessage("안녕하세요", new Date(), chatRoom, user2);

        List<ChatMessage> messageList = new ArrayList<>();
        messageList.add(chatMessage1);
        messageList.add(chatMessage2);

        given(chatMessageRepository.findAllByChatRoom(any(ChatRoom.class))).willReturn(messageList);

        // when
        List<ChatMessage> testMessageList = chatMessageService.getMessagesFromChatRoom(chatRoom);

        // then
        Assertions.assertEquals(testMessageList, messageList);
    }
}
