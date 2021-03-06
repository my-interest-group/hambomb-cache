/*
 * Copyright 2019 The  Project
 *
 * The   Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.hambomb.cache.examples.service;

import org.hambomb.cache.examples.entity.BPerson;
import org.hambomb.cache.examples.entity.Person;
import org.hambomb.cache.examples.mapper.PersonMapper;
import org.hambomb.cache.handler.annotation.AfterDeleteProcess;
import org.hambomb.cache.handler.annotation.AfterInsertProcess;
import org.hambomb.cache.handler.annotation.AfterUpdateProcess;
import org.hambomb.cache.handler.annotation.PreGetProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: <a herf="mailto:jarodchao@126.com>jarod </a>
 * @date: 2019-03-08
 */
@Service
public class PersonService {

    @Autowired
    private PersonMapper personMapper;


    @PreGetProcess(args = {"#0"})
    public Person getPersonByCardId(String cardId) {
        return personMapper.selectByCardId(cardId);
    }


    @PreGetProcess(byPrimaryKey = true)
    public Person getPersonById(Long id) {
        return personMapper.selectById(id);
    }

    @AfterUpdateProcess
    public void modifyAddressById(Person modifyPerson) {

        personMapper.updateAddressById(modifyPerson.getId(),modifyPerson.getAddress());
    }

    @AfterUpdateProcess(cacheObjectClass = Person.class)
    public void modifyPerson(ModifyPerson modifyPerson) {

        personMapper.updateAddressById(modifyPerson.getId(), modifyPerson.getAddress());

    }

    @AfterDeleteProcess(cacheObjectClass = Person.class)
    public void deletePersonById(Long id) {
        personMapper.deleteById(id);
    }

    @AfterInsertProcess
    public void insertPerson(Person person) {
        personMapper.insert(person);
    }

    @AfterInsertProcess(cacheObject = Person.class)
    public void insertPerson(BPerson person) {
        personMapper.insertB(person);
    }

}
