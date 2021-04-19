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

package utilities;

/**
 * Priority enum.
 */
public enum Priority {

  /** Task with high priority */
  HIGH("High Priority"),

  /** Task with medium priority */
  MEDIUM("Medium Priority"),

  /** Task with low priority */
  LOW("Low Priority"),

  /** Priority not set */
  DEFAULT("Not set");

  private final String description;

  Priority(String description) {
    this.description = description;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return description;
  }
}
