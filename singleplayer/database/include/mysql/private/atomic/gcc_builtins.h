#ifndef ATOMIC_GCC_BUILTINS_INCLUDED
#define ATOMIC_GCC_BUILTINS_INCLUDED

/* Copyright (c) 2008, 2010, Oracle and/or its affiliates. All rights reserved.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; version 2 of the License.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1335  USA */

#define make_atomic_add_body(S)                     \
  v= __sync_fetch_and_add(a, v);
#define make_atomic_fas_body(S)                     \
  v= __sync_lock_test_and_set(a, v);
#define make_atomic_cas_body(S)                     \
  int ## S sav;                                     \
  int ## S cmp_val= *cmp;                           \
  sav= __sync_val_compare_and_swap(a, cmp_val, set);\
  if (!(ret= (sav == cmp_val))) *cmp= sav

#ifdef MY_ATOMIC_MODE_DUMMY
#define make_atomic_load_body(S)   ret= *a
#define make_atomic_store_body(S)  *a= v
#define MY_ATOMIC_MODE "gcc-builtins-up"

#else
#define MY_ATOMIC_MODE "gcc-builtins-smp"
#define make_atomic_load_body(S)                    \
  ret= __sync_fetch_and_or(a, 0);
#define make_atomic_store_body(S)                   \
  (void) __sync_lock_test_and_set(a, v);
#endif

#endif /* ATOMIC_GCC_BUILTINS_INCLUDED */
