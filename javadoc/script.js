/*
 *     Copyright © 2021 Mona Mahmoud Mousa
 *
 *      Authors (in alphabetical order):
 *      Ask Brandsnes Røsand
 *      Joakim Skogø Langvand
 *      Leonard Sandløkk Schiller
 *      Moaaz Bassam Yanes
 *      Mona Mahmoud Mousa
 *
 *     This file is part of Todolist.
 *
 *     Todolist is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Todolist is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Todolist.  If not, see <https://www.gnu.org/licenses/>.
 */

var moduleSearchIndex;
var packageSearchIndex;
var typeSearchIndex;
var memberSearchIndex;
var tagSearchIndex;
function loadScripts(doc, tag) {
    createElem(doc, tag, 'search.js');

    createElem(doc, tag, 'module-search-index.js');
    createElem(doc, tag, 'package-search-index.js');
    createElem(doc, tag, 'type-search-index.js');
    createElem(doc, tag, 'member-search-index.js');
    createElem(doc, tag, 'tag-search-index.js');
}

function createElem(doc, tag, path) {
    var script = doc.createElement(tag);
    var scriptElement = doc.getElementsByTagName(tag)[0];
    script.src = pathtoroot + path;
    scriptElement.parentNode.insertBefore(script, scriptElement);
}

function show(type) {
    count = 0;
    for (var key in data) {
        var row = document.getElementById(key);
        if ((data[key] &  type) !== 0) {
            row.style.display = '';
            row.className = (count++ % 2) ? rowColor : altColor;
        }
        else
            row.style.display = 'none';
    }
    updateTabs(type);
}

function updateTabs(type) {
    var firstRow = document.getElementById(Object.keys(data)[0]);
    var table = firstRow.closest('table');
    for (var value in tabs) {
        var tab = document.getElementById(tabs[value][0]);
        if (value == type) {
            tab.className = activeTableTab;
            tab.innerHTML = tabs[value][1];
            tab.setAttribute('aria-selected', true);
            tab.setAttribute('tabindex',0);
            table.setAttribute('aria-labelledby', tabs[value][0]);
        }
        else {
            tab.className = tableTab;
            tab.setAttribute('aria-selected', false);
            tab.setAttribute('tabindex',-1);
            tab.setAttribute('onclick', "show("+ value + ")");
            tab.innerHTML = tabs[value][1];
        }
    }
}

function switchTab(e) {
    if (e.keyCode == 37 || e.keyCode == 38) {
        $("[aria-selected=true]").prev().click().focus();
        e.preventDefault();
    }
    if (e.keyCode == 39 || e.keyCode == 40) {
        $("[aria-selected=true]").next().click().focus();
        e.preventDefault();
    }
}

var updateSearchResults = function () {
};

function indexFilesLoaded() {
    return moduleSearchIndex
            && packageSearchIndex
            && typeSearchIndex
            && memberSearchIndex
            && tagSearchIndex;
}
