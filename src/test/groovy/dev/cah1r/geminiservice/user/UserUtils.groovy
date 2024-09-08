package dev.cah1r.geminiservice.user

import static dev.cah1r.geminiservice.user.Role.USER

class UserUtils {

    static User getTestUser_1() {
        new User(
                firstName: 'Ayrton',
                lastName: 'Senna',
                email: 'senna@cah1r.dev',
                role: USER,
                password: '$2a$10$tGeHp0JgIrn8MShA24Uzke49OcCEJ6Sa4An7Tb/CHZ62Kd.sG6k6q'
        )
    }

    static User getTestUser_2() {
        new User(
                firstName: 'Juan Manuel',
                lastName: 'Fangio',
                email: 'fangio@cah1r.dev',
                role: USER,
                password: '$2a$10$IbnNPFFEy73a271kO63C/.qwtNfq7dCSaSVH9S/6AUdLrMv6..q1G'
        )
    }
}
